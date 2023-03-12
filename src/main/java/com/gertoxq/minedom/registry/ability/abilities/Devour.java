package com.gertoxq.minedom.registry.ability.abilities;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.events.Events.MagicHitEvent;
import com.gertoxq.minedom.events.Events.RegistryDeathEvent;
import com.gertoxq.minedom.events.Events.RegistryHitEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import com.gertoxq.minedom.string.StrGen;
import net.minecraft.network.protocol.game.PacketPlayOutAnimation;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;

public class Devour extends Ability {
    public Devour() {
        super(RegistryDeathEvent.class);
    }

    @Override
    public String setName() {
        return "Devour";
    }

    @Override
    public Double setBaseDamage() {
        return null;
    }

    @Override
    public Abilitystate setState() {
        return Abilitystate.ACTIVE;
    }

    @Override
    public int setCooldown() {
        return 12;
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
    public Boolean setHasRequirement() {
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
    public void ability(EntityDamageByEntityEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(RegistryDeathEvent e, RegistryPlayer player) {
        cooldown = 0;
        if (player.player.getEquipment() == null || player.player.getEquipment().getChestplate() == null) return;
        ItemStack cp = player.player.getEquipment().getChestplate();
        RegistryItem chestplate = RegistryItem.getItemByItemStack(cp);
        if (chestplate == null) return;
        if (!playerCastMap.containsKey(player)) {
            playerCastMap.put(player, 1);
        } else {
            playerCastMap.put(player, playerCastMap.get(player)+1);
        }

        e.getEntity().entity.getWorld().spawnEntity(e.getEntity().entity.getLocation(), EntityType.EVOKER_FANGS);

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
                cooldown = setCooldown();
            }, 200L);
            player.updateStats();
            playerCastMap.remove(player);
            playerTaskMap.remove(player);
        };
        BukkitTask task = Bukkit.getScheduler().runTaskLater(Minedom.getPlugin(), runnable, 80L);
        if (playerTaskMap.containsKey(player)) {
            Bukkit.getScheduler().cancelTask(playerTaskMap.get(player));
            playerTaskMap.remove(player);
            task = Bukkit.getScheduler().runTaskLater(Minedom.getPlugin(), runnable, 80L);
            playerTaskMap.put(player, task.getTaskId());
        } else {
            playerTaskMap.put(player, task.getTaskId());
        }
    }

    @Override
    public void ability(MagicHitEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(EntityShootBowEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(ProjectileHitEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(RegistryHitEvent e, RegistryPlayer player) {

    }

}
