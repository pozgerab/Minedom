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

public class SpawnOverride implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        Bukkit.getScheduler().runTaskLater(Minedom.getPlugin(), () -> {
            RegistryEntity entity = RegistryEntity.getRegistryEntity(e.getEntity());
            if (entity != null) return;
            if (e.getEntity() instanceof LivingEntity vanilla) new VanillaEntity(vanilla);
        }, 1L);
    }
}
