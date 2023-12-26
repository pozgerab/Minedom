package com.gertoxq.minedom.registry.entity.Entities;

import com.gertoxq.minedom.Stats.EntityState;
import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.skill.Skill;
import com.gertoxq.minedom.util.StatContainter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;

public class IronGolem extends RegistryEntity {
    public IronGolem(Entity entity) {
        super("iron_golem", entity);
    }
    public IronGolem() {
        super("iron_golem");
    }

    @Override
    public @NonNull Material asItem() {
        return Material.IRON_BARS;
    }

    @Override
    public @NonNull EntityType getType() {
        return EntityType.IRON_GOLEM;
    }

    @Override
    public @NonNull StatContainter getStats() {
        return Stat.newActiveStats(200.0, 10.0, 18.0, 0.0);
    }

    @Override
    public String getName() {
        return ChatColor.BLACK + "Iron Golem";
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
    public @NonNull Skill getExpType() {
        return Skill.COMBAT;
    }

    @Override
    public double getExpDrop() {
        return 50;
    }


}
