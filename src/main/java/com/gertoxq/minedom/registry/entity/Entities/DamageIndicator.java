package com.gertoxq.minedom.registry.entity.Entities;

import com.gertoxq.minedom.StatSystem.EntityState;
import com.gertoxq.minedom.StatSystem.StatSystem;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;

public class DamageIndicator extends RegistryEntity {

    public DamageIndicator() {super("damage_indicator");}
    public DamageIndicator(Entity entity) {
        super("damage_indicator", entity);
    }

    @Override
    public EntityType setType() {
        return EntityType.ARMOR_STAND;
    }

    @Override
    public HashMap<Stats, Double> setStats() {
        return StatSystem.newPassiveStats(1.0,0.0);
    }

    @Override
    public String setName() {
        return "";
    }

    @Override
    public EntityState setState() {
        return EntityState.INDICATOR;
    }

    @Override
    public Boolean setPersistent() {
        return true;
    }

    @Override
    public double setSpawnChance() {
        return 0;
    }

    @Override
    public Skill setExpType() {
        return null;
    }

    @Override
    public double setExpDrop() {
        return 0;
    }

    @Override
    public EntityType setReplacement() {
        return null;
    }

    @Override
    public RegistryEntity setRegistryEntityReplacement() {
        return null;
    }

    public Entity spawn(Location loc) {
        this.replaceFrom = setReplacement();
        this.spawnChance = setSpawnChance();
        this.spawnLoc = loc;
        this.spawnWorld = loc.getWorld();
        this.stats = setStats();
        this.state = setState();
        this.type = setType();
        this.name = setName();
        this.persistent = setPersistent();
        this.entity = (LivingEntity) spawnWorld.spawnEntity(loc, setType());
        entity.setCustomName(name);
        this.uuid = entity.getUniqueId();
        this.entity.setGravity(false);
        entity.setInvisible(true);
        ((ArmorStand) entity).setMarker(true);
        replacement.put(replaceFrom, this);
        entities.add(this);
        return entity;
    }
}
