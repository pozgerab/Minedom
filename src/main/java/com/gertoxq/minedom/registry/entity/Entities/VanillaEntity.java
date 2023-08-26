package com.gertoxq.minedom.registry.entity.Entities;

import com.gertoxq.minedom.StatSystem.EntityState;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.skill.Skill;
import net.kyori.adventure.text.TextComponent;
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
        stats.put(Stats.HEALTH, entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        stats.put(Stats.DAMAGE, entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE) != null ? entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() : 0);
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
        stats.put(Stats.HEALTH, entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        stats.put(Stats.DAMAGE, entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE) != null ? entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() : 0);
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
    public @NonNull EntityType setType() {
        return EntityType.BAT;
    }

    @Override
    public @NonNull HashMap<Stats, Double> setStats() {
        return Stats.newEmptyPlayerStats();
    }

    @Override
    public String setName() {
        return null;
    }

    @Override
    public @NonNull EntityState setState() {
        return EntityState.ACTIVE;
    }

    @Override
    public @NonNull Boolean setPersistent() {
        return false;
    }

    @Override
    public Skill setExpType() {
        return null;
    }

    @Override
    public double setExpDrop() {
        return 0;
    }
}
