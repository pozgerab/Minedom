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
    public @NonNull EntityType setType() {
        return EntityType.IRON_GOLEM;
    }

    @Override
    public @NonNull HashMap<Stats, Double> setStats() {
        return Stats.newActiveStats(200.0, 10.0, 18.0, 0.0);
    }

    @Override
    public String setName() {
        return ChatColor.BLACK + "Iron Golem";
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
    public @NonNull Skill setExpType() {
        return Skill.COMBAT;
    }

    @Override
    public double setExpDrop() {
        return 50;
    }


}
