package com.gertoxq.minedom.registry.entity.Entities;

import com.gertoxq.minedom.StatSystem.EntityState;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;

public class DamageIndicator extends RegistryEntity {

    public DamageIndicator() {
        super("damage_indicator");
    }
    public DamageIndicator(Double dmg) {
        super("damage_indicator");
        this.name = ChatColor.RED + String.valueOf((int) Math.floor(dmg));
    }

    @Override
    public @NonNull Material asItem() {
        return null;
    }

    @Override
    public @NonNull EntityType setType() {
        return EntityType.ARMOR_STAND;
    }

    @Override
    public @NonNull HashMap<Stats, Double> setStats() {
        return Stats.newPassiveStats(1.0,0.0);
    }

    @Override
    public String setName() {
        return "";
    }

    @Override
    public @NonNull EntityState setState() {
        return EntityState.INDICATOR;
    }

    @Override
    public @NonNull Boolean setPersistent() {
        return true;
    }

    @Override
    public @NonNull Skill setExpType() {
        return null;
    }

    @Override
    public double setExpDrop() {
        return 0;
    }

    @Override
    public Entity spawn(Location loc) {
        this.spawnLoc = loc;
        this.spawnWorld = loc.getWorld();
        this.stats = setStats();
        this.state = setState();
        this.type = setType();
        this.persistent = setPersistent();
        this.entity = (LivingEntity) spawnWorld.spawnEntity(loc, setType());
        entity.setCustomName(name);
        entity.setCustomNameVisible(true);
        this.uuid = entity.getUniqueId();
        this.entity.setGravity(false);
        entity.setInvisible(true);
        ((ArmorStand) entity).setMarker(true);
        entities.add(this);
        return entity;
    }
}
