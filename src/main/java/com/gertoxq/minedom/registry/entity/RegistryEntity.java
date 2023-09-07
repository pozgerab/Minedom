package com.gertoxq.minedom.registry.entity;

import com.gertoxq.minedom.StatSystem.EntityState;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.events.Custom.Events.MagicHitEvent;
import com.gertoxq.minedom.events.Custom.Events.MeleeHitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHitEvent;
import com.gertoxq.minedom.math.DmgCalc;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
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

import static com.gertoxq.minedom.math.DmgCalc.calcMagicDmg;
import static com.gertoxq.minedom.math.DmgCalc.reduceMagicDamage;

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
    public HashMap<Stats, Double> stats;
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
    public abstract HashMap<Stats, Double> getStats();

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
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(stats.get(Stats.HEALTH));
        entity.setHealth(stats.get(Stats.HEALTH));
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
     * Deals magic damage
     * @param rawDamage Base Damage
     * @param entity Attacker
     * @Triggers {@link MagicHitEvent}
     */
    public void magicdamage(double rawDamage, RegistryEntity entity) {
        double magicDef = 0;
        if (this.stats.get(Stats.MAGIC_DEFENSE) != null) {
            magicDef = this.stats.get(Stats.MAGIC_DEFENSE);
        }
        double finalDamage = DmgCalc.toEntityMagicDmgCalc(rawDamage, entity.stats.get(Stats.MAGIC_DAMAGE), entity.stats.get(Stats.MANA), magicDef);
        new MagicHitEvent(entity, this, finalDamage).callEvent();
    }

    /**
     * Deals magic damage
     * @param ability Ability the player attacked with
     * @param player Player that attacked
     * @Triggers {@link MagicHitEvent}
     */
    public void abilitydamage(Ability ability, RegistryPlayer player) {
        double dmg = reduceMagicDamage(calcMagicDmg(player.stats.get(Stats.DAMAGE), player.stats.get(Stats.MAGIC_DAMAGE)+ability.baseDamage, player.stats.get(Stats.MANA)), this.stats.get(Stats.MAGIC_DEFENSE));
        damage(dmg, player, RegistryHitEvent.DamageSource.MAGIC);
    }

    /**
     * Damages the entity
     * @param damage Damage Amount
     * @param damager Damager Entity
     * @param source Damage Cause
     * @Triggers {@link MeleeHitEvent} | {@link RegistryHitEvent}
     */

    public void damage(double damage, RegistryEntity damager, RegistryHitEvent.DamageSource source) {
        if (source == RegistryHitEvent.DamageSource.MELEE) {
            new MeleeHitEvent(damager, this, damage).callEvent();
        } else {
            new RegistryHitEvent(damager, this, damage, source).callEvent();
        }
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

