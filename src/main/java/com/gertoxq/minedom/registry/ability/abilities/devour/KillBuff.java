package com.gertoxq.minedom.registry.ability.abilities.devour;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryDeathEvent;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class KillBuff extends AbilityAction {

    public KillBuff() {
        super(12, "devour");
    }
    public static KillBuff action = new KillBuff();
    public static HashMap<RegistryPlayer, Integer> playerCastMap = new HashMap<>();
    static HashMap<RegistryPlayer, Integer> playerTaskMap = new HashMap<>();

    @Override
    public void ability(AEvent e, RegistryPlayer player) {
        RegistryDeathEvent event = (RegistryDeathEvent) e;
        setCurrentCooldown(0);
        player.player.sendMessage(String.valueOf(this.cooldown()));
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
}
