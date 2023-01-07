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

public class Sheep extends RegistryEntity {

    public Sheep() {
        super("sheep");
    }
    public Sheep(Entity entity) {
        super("sheep", entity);
    }

    @Override
    public EntityType setType() {
        return EntityType.SHEEP;
    }

    @Override
    public HashMap<Stats, Double> setStats() {
        return StatSystem.newPassiveStats(10.0, 0.0);
    }

    @Override
    public String setName() {
        return ChatColor.WHITE + "Sheep";
    }

    @Override
    public EntityState setState() {
        return EntityState.PASSIVE;
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
        return Skill.GARDENING;
    }

    @Override
    public double setExpDrop() {
        return 10;
    }

    @Override
    public EntityType setReplacement() {
        return EntityType.SHEEP;
    }

    @Override
    public RegistryEntity setRegistryEntityReplacement() {
        return null;
    }

}
