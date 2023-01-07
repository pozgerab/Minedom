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

public class IronGolem extends RegistryEntity {
    public IronGolem(Entity entity) {
        super("iron_golem", entity);
    }
    public IronGolem() {
        super("iron_golem");
    }

    @Override
    public EntityType setType() {
        return EntityType.IRON_GOLEM;
    }

    @Override
    public HashMap<Stats, Double> setStats() {
        return StatSystem.newActiveStats(200.0, 10.0, 18.0, 0.0);
    }

    @Override
    public String setName() {
        return ChatColor.BLACK + "Iron Golem";
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
        return 1;
    }

    @Override
    public Skill setExpType() {
        return Skill.COMBAT;
    }

    @Override
    public double setExpDrop() {
        return 50;
    }

    @Override
    public EntityType setReplacement() {
        return EntityType.IRON_GOLEM;
    }

    @Override
    public RegistryEntity setRegistryEntityReplacement() {
        return null;
    }


}
