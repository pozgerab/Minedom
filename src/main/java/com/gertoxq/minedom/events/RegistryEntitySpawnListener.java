package com.gertoxq.minedom.events;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.math.RandomChance;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class RegistryEntitySpawnListener implements Listener {

    private boolean spawn = true;
    RegistryEntity newEntity;

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Entity entity = e.getEntity();
        if (!spawn) {
            spawn = true;
            return;
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> {
            if (!(entity instanceof LivingEntity)) return;
            newEntity = RegistryEntity.getRegistryEntity(entity);
            if (newEntity == null) {
                for (EntityType type : RegistryEntity.replacement.keySet()) {
                    if (entity.getType() == type) {
                        spawn = false;
                        Class<? extends RegistryEntity> entityClass = RegistryEntity.getRegistryEntityClassFromType(type).getClass();
                        try {
                            newEntity = entityClass.getDeclaredConstructor().newInstance();
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                            ex.printStackTrace();
                        }
                        newEntity.spawn(e.getLocation());
                        e.setCancelled(true);
                        entity.remove();
                    }
                }
            }
            if (!spawn) {
                spawn = true;
                return;
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> {
                RegistryEntity registryEntity = RegistryEntity.getRegistryEntity(newEntity.entity);
                if (registryEntity == null) return;
                List<RegistryEntity> registryEntityList = RegistryEntity.getRegistryEntitiesFromReplacement(registryEntity);
                if (registryEntityList == null) return;
                registryEntityList.forEach(tag -> {
                    if (RandomChance.randomChance(tag.spawnChance)) {
                        Class<? extends RegistryEntity> entityClass = RegistryEntity.getRegistryEntityClass(tag).getClass();
                        try {
                            spawn = false;
                            newEntity.entity.remove();
                            newEntity = entityClass.getDeclaredConstructor().newInstance();
                            newEntity.spawn(e.getLocation());
                        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }, 1L);
        }, 1L);
    }

}
