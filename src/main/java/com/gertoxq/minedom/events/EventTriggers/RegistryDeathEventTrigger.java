package com.gertoxq.minedom.events.EventTriggers;

import com.gertoxq.minedom.events.Events.MagicHitEvent;
import com.gertoxq.minedom.events.Events.RegistryDeathEvent;
import com.gertoxq.minedom.events.Events.RegistryHitEvent;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RegistryDeathEventTrigger implements Listener {

    @EventHandler
    public void registryDeathTriggerByDamage(RegistryHitEvent e) {
        if (e.isLock()) return;
        RegistryEntity entity = e.getTarget();
        RegistryEntity damager = e.getDamager();
        if (entity.entity.getHealth() - e.getDamage() < 0) {
            RegistryDeathEvent event = new RegistryDeathEvent(entity, damager, e.getDamage(), e.getSource());
            Bukkit.getServer().getPluginManager().callEvent(event);
            e.setCancelled(true);
        }
    }

}
