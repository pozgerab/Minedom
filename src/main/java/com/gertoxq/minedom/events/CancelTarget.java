package com.gertoxq.minedom.events;

import com.gertoxq.minedom.StatSystem.EntityState;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class CancelTarget implements Listener {

    @EventHandler
    public void onEntityTarget(EntityTargetLivingEntityEvent e) {

        RegistryEntity entity = RegistryEntity.getRegistryEntity(e.getEntity());
        if (entity == null) {
            return;
        }
        if (entity.state == EntityState.PASSIVE) {
            e.setCancelled(true);
        }

    }

}
