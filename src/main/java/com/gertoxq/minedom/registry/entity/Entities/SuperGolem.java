package com.gertoxq.minedom.registry.entity.Entities;

import com.gertoxq.minedom.StatSystem.EntityState;
import com.gertoxq.minedom.StatSystem.StatSystem;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.HashMap;

public class SuperGolem extends RegistryEntity {
    public SuperGolem() {
        super("super_golem");
    }
    public SuperGolem(Entity entity) {
        super("super_golem", entity);
    }

    @Override
    public EntityType setType() {
        return EntityType.IRON_GOLEM;
    }

    @Override
    public HashMap<Stats, Double> setStats() {
        return StatSystem.newActiveStats(500.0, 50.0, 20.0, 0.0);
    }

    @Override
    public String setName() {
        return ChatColor.RED + "Super Golem";
    }

    @Override
    public EntityState setState() {
        return EntityState.ACTIVE;
    }

    @Override
    public Boolean setPersistent() {
        return false;
    }

    @Override
    public double setSpawnChance() {
        return 0.02;
    }

    @Override
    public Skill setExpType() {
        return Skill.COMBAT;
    }

    @Override
    public double setExpDrop() {
        return 180;
    }

    @Override
    public EntityType setReplacement() {
        return null;
    }

    @Override
    public RegistryEntity setRegistryEntityReplacement() {
        return RegistryEntity.getRegistryEntityClass(new IronGolem());
    }
}
