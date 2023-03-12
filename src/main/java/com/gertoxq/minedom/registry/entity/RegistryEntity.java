package com.gertoxq.minedom.registry.entity;

import com.gertoxq.minedom.StatSystem.EntityState;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.events.Events.MagicHitEvent;
import com.gertoxq.minedom.events.Events.RegistryHitEvent;
import com.gertoxq.minedom.math.DmgCalc;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.*;
import java.util.stream.Collectors;

public abstract class RegistryEntity {
    public static ArrayList<RegistryEntity> entities = new ArrayList<>();
    public static HashMap<EntityType, RegistryEntity> replacement = new HashMap<>();
    public static HashMap<RegistryEntity, RegistryEntity> registryEntityReplacement = new HashMap<>();
    public static ArrayList<RegistryEntity> registeredEntityClasses = new ArrayList<>();

    public double spawnChance;
    public Skill expType;
    public double expDrop;
    public String ID;
    public RegistryEntity replaceRegistryEntityFrom;
    public EntityType replaceFrom;
    public LivingEntity entity;
    public EntityType type;
    public World spawnWorld;
    public Location spawnLoc;
    public HashMap<Stats, Double> stats;
    public EntityState state;
    public String name;
    public UUID uuid;
    public Boolean persistent;

    public RegistryEntity(String ID) {
        this.ID = ID;
        this.expType = setExpType();
        this.expDrop = setExpDrop();
        this.replaceRegistryEntityFrom = setRegistryEntityReplacement();
        this.replaceFrom = setReplacement();
        this.spawnChance = setSpawnChance();
        this.stats = setStats();
        this.state = setState();
        this.type = setType();
        this.name = setName();
        this.persistent = setPersistent();
    }
    public RegistryEntity(String ID, Entity entity) {
        this.ID = ID;
        this.expType = setExpType();
        this.expDrop = setExpDrop();
        this.replaceRegistryEntityFrom = setRegistryEntityReplacement();
        this.replaceFrom = setReplacement();
        this.spawnChance = setSpawnChance();
        this.stats = setStats();
        this.state = setState();
        this.type = setType();
        this.name = setName();
        this.persistent = setPersistent();
        if (name != null) {
            entity.setCustomName(name);
        }
        this.uuid = entity.getUniqueId();
        this.entity = (LivingEntity) entity;
        if (replaceRegistryEntityFrom != null) registryEntityReplacement.put(replaceRegistryEntityFrom, this);
        entities.add(this);
        replacement.put(replaceFrom, this);
    }

    public abstract EntityType setType();

    public abstract HashMap<Stats, Double> setStats();

    public abstract String setName();

    public abstract EntityState setState();

    public abstract Boolean setPersistent();

    public abstract double setSpawnChance();

    public abstract Skill setExpType();

    public abstract double setExpDrop();

    public abstract EntityType setReplacement();

    public abstract RegistryEntity setRegistryEntityReplacement();

    public void register() {
        spawn(new Location(Bukkit.getWorlds().get(0), 0.0, 0.0, 0.0)).remove();
        registeredEntityClasses.add(this);
    }
    public Entity spawn(Location loc) {
        this.spawnLoc = loc;
        this.spawnWorld = loc.getWorld();
        this.entity = (LivingEntity) spawnWorld.spawnEntity(loc, setType());
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(stats.get(Stats.HEALTH));
        entity.setHealth(stats.get(Stats.HEALTH));
        if (name != null) {
            entity.setCustomName(name);
        }
        this.uuid = entity.getUniqueId();
        if (replaceRegistryEntityFrom != null) registryEntityReplacement.put(replaceRegistryEntityFrom, this);
        replacement.put(replaceFrom, this);
        entities.add(this);
        return entity;
    }

    public void kill(RegistryEntity killer, RegistryHitEvent.DamageSource source) {
        RegistryHitEvent event = new RegistryHitEvent(killer, this, entity.getHealth()+1, source, true);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public void magicdamage(double rawDamage, RegistryEntity entity) {
        double magicDef = 0;
        if (this.stats.get(Stats.MAGIC_DEFENSE) != null) {
            magicDef = this.stats.get(Stats.MAGIC_DEFENSE);
        }
        double finalDamage = DmgCalc.toEntityMagicDmgCalc(rawDamage, entity.stats.get(Stats.MAGIC_DAMAGE), entity.stats.get(Stats.MANA), magicDef);
        MagicHitEvent event = new MagicHitEvent(entity, this, finalDamage);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public void damage(double damage, RegistryEntity damager, RegistryHitEvent.DamageSource source) {
        RegistryHitEvent event = new RegistryHitEvent(damager, this, damage, source);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public static RegistryEntity getRegistryEntity(Entity entity) {
        return entities.stream().filter(e -> e.uuid == entity.getUniqueId()).findAny().orElse(null);
    }

    public static RegistryEntity getRegistryEntityClass(RegistryEntity from) {
        return registeredEntityClasses.stream().filter(entity -> Objects.equals(entity.ID, from.ID)).findAny().orElse(null);
    }

    public static List<Entity> filterRegisteredEntities(List<Entity> entities) {
        return entities.stream().filter(entity -> entity != null && getRegistryEntity(entity) != null && !getRegistryEntity(entity).persistent).collect(Collectors.toList());
    }

    public static List<RegistryEntity> getRegistryEntitiesFromReplacement(RegistryEntity entity) {
        return registeredEntityClasses.stream().filter(e -> {
            if (e.replaceRegistryEntityFrom != null) {
                return Objects.equals(e.replaceRegistryEntityFrom.ID, entity.ID);
            }
            return false;
        }).collect(Collectors.toList());
    }

    public static RegistryEntity getRegistryEntityClassFromType(EntityType type) {
        return registeredEntityClasses.stream().filter(e -> e.type == type).findAny().orElse(null);
    }

    public static boolean compare(RegistryEntity entity1, RegistryEntity entity2) {
        return entity1.uuid == entity2.uuid;
    }

}

