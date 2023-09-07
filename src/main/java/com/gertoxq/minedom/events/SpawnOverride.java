package com.gertoxq.minedom.events;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.registry.entity.Entities.VanillaEntity;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Listens for entity spawn event. If an entity isn't a custom entity, it gets converted to custom one.
 */
public class SpawnOverride implements Listener {

    /**
     * Override the entity if it's not a custom type
     * @param e Entity spawn event
     */
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        Bukkit.getScheduler().runTaskLater(Minedom.getPlugin(), () -> {
            RegistryEntity entity = RegistryEntity.getRegistryEntity(e.getEntity());
            if (entity != null) return;
            if (e.getEntity() instanceof LivingEntity vanilla) new VanillaEntity(vanilla);
        }, 1L);
    }
}
