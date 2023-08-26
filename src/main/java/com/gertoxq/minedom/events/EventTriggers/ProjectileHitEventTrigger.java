package com.gertoxq.minedom.events.EventTriggers;

import com.gertoxq.minedom.events.Custom.Events.ProjectileHitEvent;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ProjectileHitEventTrigger implements Listener {

    @EventHandler
    public void onProjectileHit(org.bukkit.event.entity.ProjectileHitEvent e) {
        RegistryEntity registryEntity = RegistryEntity.getRegistryEntity(e.getHitEntity());
        new ProjectileHitEvent(e.getEntity(), registryEntity);
    }
}
