package com.gertoxq.minedom.registry.entity.Entities;

import com.gertoxq.minedom.Stats.EntityState;
import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.skill.Skill;
import com.gertoxq.minedom.util.StatContainter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;

public class VanillaEntity extends RegistryEntity {

    EntityType vanillaType;
    Entity nonAliveEntity;
    public VanillaEntity() {
        super("#def");
    }
    public VanillaEntity(EntityType type) {
        super("#def");
        this.vanillaType = type;
    }
    public VanillaEntity(LivingEntity entity) {
        super("#def");
        this.entity = entity;
        type = entity.getType();
        stats.put(Stat.HEALTH, entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        stats.put(Stat.DAMAGE, entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE) != null ? entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() : 0);
        StringBuilder result = new StringBuilder();
        String[] words = type.toString().toLowerCase().split("_");
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }
        name = result.toString().trim();
        entity.setCustomNameVisible(true);
        this.uuid = entity.getUniqueId();
        entities.add(this);
    }

    @Override
    public Entity spawn(Location loc) {
        this.spawnLoc = loc;
        this.spawnWorld = loc.getWorld();
        if (vanillaType != null) type = vanillaType;
        this.nonAliveEntity = spawnWorld.spawnEntity(loc, type);
        if (!(nonAliveEntity instanceof LivingEntity)) return nonAliveEntity;
        this.entity = (LivingEntity) nonAliveEntity;
        stats.put(Stat.HEALTH, entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        stats.put(Stat.DAMAGE, entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE) != null ? entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() : 0);
        StringBuilder result = new StringBuilder();
        String[] words = type.toString().toLowerCase().split("_");
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }
        name = result.toString().trim();
        entity.setCustomNameVisible(true);
        this.uuid = entity.getUniqueId();
        entities.add(this);
        return entity;
    }

    @Override
    public @NonNull Material asItem() {
        return Material.GHAST_SPAWN_EGG;
    }

    @Override
    public @NonNull EntityType getType() {
        return EntityType.BAT;
    }

    @Override
    public @NonNull StatContainter getStats() {
        return Stat.newEmptyPlayerStats();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public @NonNull EntityState getState() {
        return EntityState.ACTIVE;
    }

    @Override
    public @NonNull Boolean getPersistent() {
        return false;
    }

    @Override
    public Skill getExpType() {
        return null;
    }

    @Override
    public double getExpDrop() {
        return 0;
    }
}
