package com.gertoxq.minedom.registry.ability.abilities;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.InitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryDeathEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import com.gertoxq.minedom.registry.ability.TriggerFace.DeathAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.InitAbility;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

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
    HashMap<RegistryPlayer, Integer> playerTaskMap = new HashMap<>();

    @Override
    public AbilityAction ability(RegistryDeathEvent e, RegistryPlayer player) {
        return new AbilityInterface.AbilityAction(12) {

            @Override
            public void ability(AEvent e, RegistryPlayer player) {
                RegistryDeathEvent event = (RegistryDeathEvent) e;
                cooldown = 0;
                player.player.getEquipment();
                if (player.player.getEquipment().getChestplate() == null) return;
                ItemStack cp = player.player.getEquipment().getChestplate();
                RegistryItem chestplate = RegistryItem.getItemByItemStack(cp);
                if (chestplate == null) return;
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
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> {
                        if (player.player.getAbsorptionAmount() - statLoss < 0) {
                            player.player.setAbsorptionAmount(0);
                        } else {
                            player.player.setAbsorptionAmount(player.player.getAbsorptionAmount() - statLoss);
                        }
                        cooldown = cooldown();
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

    int ROTATE_PER_TICK = 20;
    double MOVE_PER_TICK = 0.1;
    boolean isActive = false;
    HashMap<RegistryPlayer, Integer> haloMap = new HashMap<>();

    @Override
    public InitAbility.AbilityAction ability(InitEvent e, RegistryPlayer player) {
        return new StateAction() {
            @Override
            public void cleanUp(RegistryPlayer player) {
                if (haloMap.get(player) != null) Bukkit.getScheduler().cancelTask(haloMap.get(player));
            }

            @Override
            public void ability(AEvent e, RegistryPlayer player) {
                if (isActive) {
                    return;
                }
                isActive = true;
                Runnable task = new Runnable() {

                    boolean up = true;
                    boolean left = true;
                    boolean back = true;
                    final Vector upOffset = new Vector(0,0,0);
                    final Vector lOffset = new Vector(0,0,0);
                    final Vector bOffset = new Vector(0,0,0);
                    final Vector upRotator = new Vector(0,0,1.2);
                    final Vector lRotator = new Vector(0,1.2,0);
                    final Vector bRotator = new Vector(0,1.2,0);
                    @Override
                    public void run() {
                        World world = player.player.getWorld();
                        Location ustart = player.player.getLocation();
                        Location lstart = player.player.getLocation().add(new Vector(0,1,-1));
                        Location bstart = player.player.getLocation().add(new Vector(-1,1,0));
                        Location uend = ustart.clone().add(new Vector(0, 2, 0));
                        Location lend = lstart.clone().add(new Vector(0, 0, 2));
                        Location bend = bstart.clone().add(new Vector(2, 0, 0));
                        Location umovable = ustart.clone().add(upOffset);
                        Location lmovable = lstart.clone().add(lOffset);
                        Location bmovable = bstart.clone().add(bOffset);
                        if ((umovable.getY() == uend.getY()+MOVE_PER_TICK || umovable.getY() > uend.getY()+MOVE_PER_TICK) || (umovable.getY() == ustart.getY()-MOVE_PER_TICK || umovable.getY() < ustart.getY()-MOVE_PER_TICK)) {
                            up = !up;
                        }
                        if ((lmovable.getZ() == lend.getZ()+MOVE_PER_TICK || lmovable.getZ() > lend.getZ()+MOVE_PER_TICK) || (lmovable.getZ() == lstart.getZ()-MOVE_PER_TICK || lmovable.getZ() < lstart.getZ()-MOVE_PER_TICK)) {
                            left = !left;
                        }
                        if ((bmovable.getX() == bend.getX()+MOVE_PER_TICK || bmovable.getX() > bend.getX()+MOVE_PER_TICK) || (bmovable.getX() == bstart.getX()-MOVE_PER_TICK || bmovable.getX() < bstart.getX()-MOVE_PER_TICK)) {
                            back = !back;
                        }
                        world.spawnParticle(Particle.REDSTONE, umovable.clone().add(upRotator), 1, 0.01, 0.01, 0.01, 0, new Particle.DustOptions(Color.GREEN, 1.5f));
                        world.spawnParticle(Particle.REDSTONE, lmovable.clone().add(lRotator), 1, 0.01, 0.01, 0.01, 0, new Particle.DustOptions(Color.BLUE, 1.5f));
                        world.spawnParticle(Particle.REDSTONE, bmovable.clone().add(bRotator), 1, 0.01, 0.01, 0.01, 0, new Particle.DustOptions(Color.RED, 1.5f));
                        //world.spawnParticle(Particle.END_ROD, umovable.clone().add(upRotator.clone().rotateAroundY(Math.toRadians(180))), 20, 0.01, 0.01, 0.01, 0);
                        upRotator.rotateAroundY(Math.toRadians(ROTATE_PER_TICK));
                        upOffset.add(new Vector(0, up ? MOVE_PER_TICK : -MOVE_PER_TICK, 0));
                        lRotator.rotateAroundZ(Math.toRadians(ROTATE_PER_TICK));
                        lOffset.add(new Vector(0, 0, left ? MOVE_PER_TICK : -MOVE_PER_TICK));
                        bRotator.rotateAroundX(Math.toRadians(ROTATE_PER_TICK));
                        bOffset.add(new Vector(back ? MOVE_PER_TICK : -MOVE_PER_TICK, 0, 0));
                    }
                };

                int num = Bukkit.getScheduler().scheduleSyncRepeatingTask(Minedom.getPlugin(), task, 0L, 1L);
                haloMap.put(player, num);
            }
        };
    }
}
