package com.gertoxq.minedom.events;

import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        RegistryEntity registryEntity = RegistryEntity.getRegistryEntity(entity);
        if (registryEntity == null) {return;}
        RegistryEntity.entities.remove(registryEntity);
    }
}
