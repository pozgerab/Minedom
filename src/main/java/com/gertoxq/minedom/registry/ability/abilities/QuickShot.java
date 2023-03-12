package com.gertoxq.minedom.registry.ability.abilities;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.events.Events.MagicHitEvent;
import com.gertoxq.minedom.events.Events.RegistryDeathEvent;
import com.gertoxq.minedom.events.Events.RegistryHitEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class QuickShot extends Ability {
    public QuickShot() {
        super(PlayerInteractEvent.class);
    }

    @Override
    public String setName() {
        return "Quickshot";
    }

    @Override
    public Double setBaseDamage() {
        return 0.0;
    }

    @Override
    public Abilitystate setState() {
        return Abilitystate.PASSIVE;
    }

    @Override
    public int setCooldown() {
        return 0;
    }

    @Override
    public ArrayList<String> setLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+"Instantly shoots after releasing");
        return lore;
    }

    @Override
    public Boolean setHasRequirement() {
        return false;
    }

    @Override
    public Skill setRequirementType() {
        return null;
    }

    @Override
    public int setRequirementLevel() {
        return 0;
    }

    @Override
    public void ability(EntityDamageByEntityEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(RegistryDeathEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(MagicHitEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(EntityShootBowEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(ProjectileHitEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(RegistryHitEvent e, RegistryPlayer player) {

    }
}
