package com.gertoxq.minedom.events;

import com.gertoxq.minedom.Stats.EntityState;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

/**
 * Cancels passive mob aggro
 */
public class CancelTarget implements Listener {
    /**
     * Listens to entity target livingentity event and cancels if state is {@link EntityState#PASSIVE}
     * @param e {@link EntityTargetLivingEntityEvent}
     */
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
