package com.gertoxq.minedom.events.Custom.Events.RegistryDeath;

import com.gertoxq.minedom.events.Custom.Events.RegistryHit.RegistryHitEvent;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class RegistryDeathEventTrigger implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void registryDeathTriggerByDamage(RegistryHitEvent e) {
        if (e.isLock() || e.isCancelled()) return;
        RegistryEntity entity = e.getEntity();
        RegistryEntity damager = e.getDamager();
        if (entity.entity.getHealth() - e.getDamage() <= 0) {
            e.setDeadly(true);
            RegistryDeathEvent event = new KillEvent(entity, damager, e.getDamage(), e.getSource());
            e.setDeathEvent(event);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

}
