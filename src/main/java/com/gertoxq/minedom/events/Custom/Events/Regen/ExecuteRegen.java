package com.gertoxq.minedom.events.Custom.Events.Regen;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.events.Custom.Events.Regen.RegenEvent;
import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.registry.entity.Entities.DamageIndicator;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public class ExecuteRegen implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void executeRegen(RegenEvent e) {
        if (e.isCancelled()) return;
        RegistryEntity entity = e.getHealed();
        Location location = entity.entity.getLocation();

        double healAmount = e.getHealed().entity.getHealth() + e.getAmount() - e.getHealed().entity.getHealth();
        if (healAmount == 0) return;
        entity.entity.setHealth(Math.min(entity.entity.getHealth() + e.getAmount(), entity.stats.getHEALTH()));

        DamageIndicator indicator = new DamageIndicator(healAmount, ChatColor.GREEN);

        indicator.spawn(location.add(new Vector(Math.random()*2.4-1.2, Math.random()*2.4-1.2, Math.random()*2.4-1.2)));

        Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> indicator.entity.remove(), 20L);
    }
}
