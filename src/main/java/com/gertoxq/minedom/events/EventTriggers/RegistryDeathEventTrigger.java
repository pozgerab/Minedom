package com.gertoxq.minedom.events.EventTriggers;

import com.gertoxq.minedom.events.Events.MagicHitEvent;
import com.gertoxq.minedom.events.Events.RegistryDeathEvent;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class RegistryDeathEventTrigger implements Listener {

    @EventHandler
    public void registryDeathTriggerByDamage(EntityDamageByEntityEvent e) {
        RegistryEntity entity = RegistryEntity.getRegistryEntity(e.getEntity());
        RegistryEntity damager = RegistryEntity.getRegistryEntity(e.getDamager());
        if (entity == null || damager == null) return;
        if (entity.entity.getHealth() - e.getDamage() < 0) {
            RegistryDeathEvent event = new RegistryDeathEvent(entity, damager);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    @EventHandler
    public void registryDeathTriggerByMagic(MagicHitEvent e) {
        RegistryEntity entity = e.getTarget();
        RegistryEntity damager = e.getDamager();
        if (entity == null || damager == null) return;
        if (entity.entity.isDead()) return;
        if (entity.entity.getHealth() - e.getDamage() < 0) {
            RegistryDeathEvent event = new RegistryDeathEvent(entity, damager);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

}
