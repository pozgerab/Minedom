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

public class Zombie extends RegistryEntity {
    public Zombie() {
        super("zombie");
    }
    public Zombie(Entity entity) {
        super("zombie", entity);
    }

    @Override
    public EntityType setType() {
        return EntityType.ZOMBIE;
    }

    @Override
    public HashMap<Stats, Double> setStats() {
        return StatSystem.newActiveStats(20.0, 0.0, 3.5, 0.0);
    }

    @Override
    public String setName() {
        return ChatColor.DARK_GRAY+"Zombie";
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
        return 15;
    }

    @Override
    public EntityType setReplacement() {
        return EntityType.ZOMBIE;
    }

    @Override
    public RegistryEntity setRegistryEntityReplacement() {
        return null;
    }
}
