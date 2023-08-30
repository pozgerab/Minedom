package com.gertoxq.minedom.registry.ability.abilities;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryDeathEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.TriggerFace.*;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;

public class Devour extends Ability implements DeathAbility, InitAbility {

    @Override
    public String setName() {
        return "Devour";
    }

    @Override
    public String setId() {
        return "devour";
    }

    @Override
    public double setBaseDamage() {
        return 0.0;
    }

    @Override
    public AbilityState setState() {
        return AbilityState.FULL_ARMOR;
    }

    @Override
    public int setCooldown() {
        return 12;
    }

    @Override
    public TriggerType setTriggerType() {
        return TriggerType.FULL_ARMOR;
    }

    @Override
    public ArrayList<String> setLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "On killing an enemy, get 20 " + Stats.AGILITY.displayName);
        lore.add(ChatColor.GRAY + "and 10 " + Stats.STRENGTH.displayName + " for 4 seconds.");
        lore.add(ChatColor.GRAY + "On expiring, the lost buffs get");
        lore.add(ChatColor.GRAY + "converted to absorption for 10s.");
        lore.add(ChatColor.DARK_GRAY + "(Buffs and absorption stack)");
        return lore;
    }

    @Override
    public boolean setHasRequirement() {
        return false;
    }

    @Override
    public Skill setRequirementType() {
        return null;
    }

    @Override
    public int setRequirementLevel() {
        return 0;
    }


    public static HashMap<RegistryPlayer, Integer> playerCastMap = new HashMap<>();
    static HashMap<RegistryPlayer, Integer> playerTaskMap = new HashMap<>();

    @Override
    public AbilityAction ability(DeathAbility classs) {
        return new AbilityAction(12) {

            @Override
            public void ability(AEvent e, RegistryPlayer player) {
                RegistryDeathEvent event = (RegistryDeathEvent) e;
                setCurrentCooldown(0);
                if (!playerCastMap.containsKey(player)) {
                    playerCastMap.put(player, 1);
                } else {
                    playerCastMap.put(player, playerCastMap.get(player)+1);
                }

                event.getEntity().entity.getWorld().spawnEntity(event.getEntity().entity.getLocation(), EntityType.EVOKER_FANGS);
                event.getEntity().entity.getWorld().spawnParticle(Particle.BLOCK_CRACK, event.getEntity().entity.getLocation().add(0.2,1.5,0.2), 100, Material.RED_WOOL.createBlockData());

                player.abilityStats.put(Stats.AGILITY, player.abilityStats.get(Stats.AGILITY) + 20);
                player.abilityStats.put(Stats.STRENGTH, player.abilityStats.get(Stats.STRENGTH) + 10);
                player.updateStats();

                Runnable runnable = () -> {
                    if (!playerCastMap.containsKey(player)) return;
                    int strLoss = 10 * playerCastMap.get(player);
                    int agLoss = 20 * playerCastMap.get(player);
                    int statLoss = strLoss + agLoss;
                    player.abilityStats.put(Stats.AGILITY, player.abilityStats.get(Stats.AGILITY) - agLoss);
                    player.abilityStats.put(Stats.STRENGTH, player.abilityStats.get(Stats.STRENGTH) - strLoss);
                    player.player.setAbsorptionAmount(player.player.getAbsorptionAmount() + statLoss);
                    resetCooldown();
                    player.player.sendMessage(String.valueOf(this.cooldown()));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> {
                        if (player.player.getAbsorptionAmount() - statLoss < 0) {
                            player.player.setAbsorptionAmount(0);
                        } else {
                            player.player.setAbsorptionAmount(player.player.getAbsorptionAmount() - statLoss);
                        }
                    }, 200L);
                    player.updateStats();
                    playerCastMap.remove(player);
                    playerTaskMap.remove(player);
                };
                if (playerTaskMap.containsKey(player)) {
                    Bukkit.getScheduler().cancelTask(playerTaskMap.get(player));
                    playerTaskMap.remove(player);
                    BukkitTask task = Bukkit.getScheduler().runTaskLater(Minedom.getPlugin(), runnable, 80L);
                    playerTaskMap.put(player, task.getTaskId());
                } else {
                    BukkitTask task = Bukkit.getScheduler().runTaskLater(Minedom.getPlugin(), runnable, 80L);
                    playerTaskMap.put(player, task.getTaskId());
                }
            }
        };
    }

    @Override
    public AbilityAction ability(InitAbility classs) {
        return new Halo();
    }
}
