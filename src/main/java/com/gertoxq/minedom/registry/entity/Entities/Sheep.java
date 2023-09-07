package com.gertoxq.minedom.registry.entity.Entities;

import com.gertoxq.minedom.StatSystem.EntityState;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;

public class Sheep extends RegistryEntity {

    public Sheep() {
        super("sheep");
    }
    public Sheep(Entity entity) {
        super("sheep", entity);
    }

    @Override
    public @NonNull Material asItem() {
        return Material.MUTTON;
    }

    @Override
    public @NonNull EntityType getType() {
        return EntityType.SHEEP;
    }

    @Override
    public @NonNull HashMap<Stats, Double> getStats() {
        return Stats.newPassiveStats(10.0, 0.0);
    }

    @Override
    public String getName() {
        return ChatColor.WHITE + "Sheep";
    }

    @Override
    public @NonNull EntityState getState() {
        return EntityState.PASSIVE;
    }

    @Override
    public @NonNull Boolean getPersistent() {
        return false;
    }

    @Override
    public @NonNull Skill getExpType() {
        return Skill.GARDENING;
    }

    @Override
    public double getExpDrop() {
        return 10;
    }

}
