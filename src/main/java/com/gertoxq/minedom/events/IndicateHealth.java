package com.gertoxq.minedom.events;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.events.Custom.Events.RegistryHitEvent;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.entity.Entities.DamageIndicator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class IndicateHealth implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {

        Entity victim = e.getEntity();
        RegistryEntity entity = RegistryEntity.getRegistryEntity(victim);
        if (entity == null || entity.persistent) {return;}
        Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> {
            entity.entity.setCustomName(entity.name + ChatColor.GRAY + " [" +
                    ChatColor.GREEN + entity.entity.getHealth() +
                    ChatColor.GRAY + "/" +
                    ChatColor.GREEN + entity.entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() +
                    ChatColor.GRAY + "]");
        }, 2L);
    }

    @EventHandler
    public void onDamage(RegistryHitEvent e) {
        RegistryEntity entity = e.getEntity();
        if (entity == null) return;
        if (entity.persistent) {e.setCancelled(true); return;}
        Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> {

            entity.entity.setCustomName(entity.name + ChatColor.GRAY + " [" +
                    ChatColor.GREEN + (int) Math.floor(entity.entity.getHealth()) +
                    ChatColor.GRAY + "/" +
                    ChatColor.GREEN + (int) Math.floor(entity.entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) +
                    ChatColor.GRAY + "]");

            DamageIndicator indicator = new DamageIndicator(e.getDamage());

            indicator.spawn(entity.entity.getLocation());
            Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> {
                indicator.entity.remove();
            }, 60L);

        }, 2L);

    }

}
