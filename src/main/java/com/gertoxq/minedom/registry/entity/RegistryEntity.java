package com.gertoxq.minedom.registry.entity;

import com.gertoxq.minedom.Stats.EntityState;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.MagicHitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.MeleeHitEvent;
import com.gertoxq.minedom.events.Custom.Events.Regen.RegenEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.RecieveHitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.RegistryHitEvent;
import com.gertoxq.minedom.skill.Skill;
import com.gertoxq.minedom.util.StatContainter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Custom entity class. The extensions of this class represents custom entity types, and the instances of those classes represent custom entities.
 */
public abstract class RegistryEntity {
    /**
     * List of all living custom entities
     */
    public static ArrayList<RegistryEntity> entities = new ArrayList<>();
    /**
     * Instances of all registered entities
     */
    public static ArrayList<RegistryEntity> registeredEntityClasses = new ArrayList<>();

    /**
     * Skill type that the mob's killer receives
     */
    public Skill expType;
    /**
     * Skill exp that the mob's killer receives
     */
    public double expDrop;
    /**
     * Unique identifier
     */
    public String ID;
    /**
     * Bukkit living entity
     */
    public LivingEntity entity;
    /**
     * Entity type
     */
    public EntityType type;
    /**
     * World that the entity spawns in
     */
    public World spawnWorld;
    /**
     * Location that the entity spawn on
     */
    public Location spawnLoc;
    /**
     * Entities custom stats
     */
    public StatContainter stats = new StatContainter();
    /**
     * Custom entity state, represents behaviour
     */
    public EntityState state;
    /**
     * Entity display name
     */
    public String name;
    /**
     * Entity UUID
     */
    public UUID uuid;
    /**
     * Whether the entity is persistent (unattainable)
     */
    public Boolean persistent;
    /**
     * Entity's material representation
     */
    public final Material asItem;

    /**
     * Create a new instance of a custom entity type with an entity type id (same custom entity types share the same id)
     * @param ID Unique custom entity type identifier
     */
    public RegistryEntity(String ID) {
        this.ID = ID;
        this.asItem = asItem();
        this.expType = getExpType();
        this.expDrop = getExpDrop();
        this.stats = getStats();
        this.state = getState();
        this.type = getType();
        this.name = getName();
        this.persistent = getPersistent();
    }

    /**
     * Creates a new entity from an existing bukkit entity
     * @param ID Unique custom entity type identifier, this is going to be the entity's type
     * @param entity Bukkit entity
     */
    public RegistryEntity(String ID, Entity entity) {
        this.ID = ID;
        this.asItem = asItem();
        this.expType = getExpType();
        this.expDrop = getExpDrop();
        this.stats = getStats();
        this.state = getState();
        this.type = getType();
        this.name = getName();
        this.persistent = getPersistent();
        if (name != null) {
            entity.setCustomName(name);
        }
        this.uuid = entity.getUniqueId();
        this.entity = (LivingEntity) entity;
        entities.add(this);
    }

    /**
     * @return This entity type's Representative material
     */
    @NotNull
    public abstract Material asItem();

    /**
     * @return This custom entity type's Bukkit entity type
     */
    @NotNull
    public abstract EntityType getType();

    /**
     * @return The entity's stats
     */
    @NotNull
    public abstract StatContainter getStats();

    /**
     * @return Entity's display name
     */
    public abstract String getName();

    /**
     * @return Entity's behaviour
     */
    @NotNull
    public abstract EntityState getState();

    /**
     * @return Whether the entity is unattainable
     */
    @NotNull
    public abstract Boolean getPersistent();

    /**
     * @return Entity's exp gained upon killing it
     */
    @Nullable
    public abstract Skill getExpType();

    /**
     * @return Entity's exp amount gained upon killing it
     */
    public abstract double getExpDrop();

    /**
     * Used to register the entity
     */
    public void register() {
        spawn(new Location(Bukkit.getWorlds().get(0), 0.0, 0.0, 0.0)).remove();
        registeredEntityClasses.add(this);
    }

    /**
     * Spawns the entity
     * @param loc Spawn location
     * @return The Bukkit entity
     */
    public Entity spawn(Location loc) {
        this.spawnLoc = loc;
        this.spawnWorld = loc.getWorld();
        this.entity = (LivingEntity) spawnWorld.spawnEntity(loc, getType());
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(stats.getHEALTH());
        entity.setHealth(stats.getHEALTH());
        if (name != null) {
            entity.setCustomName(name);
        }
        this.uuid = entity.getUniqueId();
        entities.add(this);
        return entity;
    }

    /**
     * Kills the entity.
     * Sets health to 0 in case the entity didn't get the damage
     */
    public void remove() {
        entity.setHealth(0);
        entities.remove(this);
    }

    /**
     * Kills the entity by another entity
     * @param killer Specifies the killer entity
     * @param source Damage cause
     */
    public void kill(RegistryEntity killer, RegistryHitEvent.DamageSource source) {
        this.remove();
    }

    /**
     * Damages the entity
     * @param damage Damage Amount
     * @param damager Damager Entity
     * @param source Damage Cause
     * @Triggers {@link MeleeHitEvent} | {@link RegistryHitEvent}
     */

    public void damage(double damage, RegistryEntity damager, RegistryHitEvent.DamageSource source) {
        (switch (source) {
            case MELEE -> new MeleeHitEvent(damager, this, damage);
            case MAGIC -> new MagicHitEvent(damager, this, damage);
            default -> new RecieveHitEvent(damager, this, damage, source);
        }).callEvent();
    }
    public void damage(double damage, RegistryEntity damager, RegistryHitEvent.DamageSource source, boolean lock) {
        (switch (source) {
            case MELEE -> new MeleeHitEvent(damager, this, damage, lock);
            case MAGIC -> new MagicHitEvent(damager, this, damage, lock);
            default -> new RecieveHitEvent(damager, this, damage, source, lock);
        }).callEvent();
    }

    public void heal(double amount, RegenEvent.RegenCause source) {
        new RegenEvent(this, amount, source).callEvent();
    }

    public void healByEntity(double amount, RegistryEntity healer) {
        new RegenEvent(this, amount, healer).callEvent();
    }

    /**
     * Gets entity by Bukkit entity
     * @param entity Bukkit entity
     * @return Registry Entity
     */
    public static RegistryEntity getRegistryEntity(Entity entity) {
        return entity != null ? entities.stream().filter(e -> e.uuid == entity.getUniqueId()).findAny().orElse(null) : null;
    }

    /**
     * Returns entity by ID
     * @param id ID
     * @return Found entity
     */
    public static RegistryEntity byId(String id) {
        return registeredEntityClasses.stream().filter(entity -> Objects.equals(entity.ID, id)).findAny().orElse(null);
    }

    /**
     * Gets an instance of a registry entity
     * @param from Entity
     * @return New entity instance
     */
    public static RegistryEntity getRegistryEntityClass(RegistryEntity from) {
        return registeredEntityClasses.stream().filter(entity -> Objects.equals(entity.ID, from.ID)).findAny().orElse(null);
    }

    /**
     * Filters Registry Entities from Bukkit entity list
     * @param entities Bukkit entity list
     * @return Registry Entity list
     */
    public static List<RegistryEntity> filterRegisteredEntities(List<Entity> entities) {
        return entities.stream().filter(Objects::nonNull).map(entity1 -> {
            if (getRegistryEntity(entity1) != null && getRegistryEntity(entity1).state != EntityState.INDICATOR) return getRegistryEntity(entity1);
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Gets Registry Entity instances by an entity type
     * @param type Entity type
     * @return Registry Entity instance list
     */
    public static List<RegistryEntity> getRegistryEntityClassFromType(EntityType type) {
        return registeredEntityClasses.stream().filter(e -> e.type == type).toList();
    }

    /**
     * Checks if two entities are the same
     * @param entity1
     * @param entity2
     */
    public static boolean equals(RegistryEntity entity1, RegistryEntity entity2) {
        return entity1.uuid == entity2.uuid;
    }

    /**
     * Checks if an entity is in a category
     * @param category Category to check
     */
    public boolean isInCategory(EntityCategory category) {
        return category.types.contains(this);
    }

}

