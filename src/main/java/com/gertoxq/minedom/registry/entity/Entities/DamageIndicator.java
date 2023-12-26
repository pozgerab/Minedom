package com.gertoxq.minedom.registry.entity.Entities;

import com.gertoxq.minedom.Stats.EntityState;
import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.skill.Skill;
import com.gertoxq.minedom.util.StatContainter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

public class DamageIndicator extends RegistryEntity {

    public DamageIndicator() {
        super("damage_indicator");
    }
    public DamageIndicator(Double dmg) {
        super("damage_indicator");
        NumberFormat formatter = new DecimalFormat("#0.0");
        this.name = ChatColor.RED+formatter.format(dmg);
    }
    public DamageIndicator(Double dmg, ChatColor color) {
        super("damage_indicator");
        NumberFormat formatter = new DecimalFormat("#0.0");
        this.name = color + formatter.format(dmg);
    }

    @Override
    public @NonNull Material asItem() {
        return null;
    }

    @Override
    public @NonNull EntityType getType() {
        return EntityType.ARMOR_STAND;
    }

    @Override
    public @NonNull StatContainter getStats() {
        return Stat.newPassiveStats(1.0,0.0);
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public @NonNull EntityState getState() {
        return EntityState.INDICATOR;
    }

    @Override
    public @NonNull Boolean getPersistent() {
        return true;
    }

    @Override
    public @NonNull Skill getExpType() {
        return null;
    }

    @Override
    public double getExpDrop() {
        return 0;
    }

    @Override
    public Entity spawn(Location loc) {
        this.spawnLoc = loc;
        this.spawnWorld = loc.getWorld();
        this.stats = getStats();
        this.state = getState();
        this.type = getType();
        this.persistent = getPersistent();
        this.entity = (LivingEntity) spawnWorld.spawnEntity(loc.clone().add(new Vector(0,+100,0)), getType());
        entity.setCustomName(name);
        entity.setCustomNameVisible(true);
        this.uuid = entity.getUniqueId();
        this.entity.setGravity(false);
        entity.setInvisible(true);
        entity.setSilent(true);
        ((ArmorStand) entity).setMarker(true);
        this.entity.teleport(loc);
        entities.add(this);
        return entity;
    }
}
