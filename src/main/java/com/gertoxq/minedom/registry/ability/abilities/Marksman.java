package com.gertoxq.minedom.registry.ability.abilities;

import com.gertoxq.minedom.events.Events.MagicHitEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.*;

import java.util.ArrayList;

public class Marksman extends Ability {
    public Marksman() {
        super(EntityShootBowEvent.class);
    }

    @Override
    public String setName() {
        return "";
    }

    @Override
    public Double setBaseDamage() {
        return 0.0;
    }

    @Override
    public Ability.Abilitystate setState() {
        return Abilitystate.PASSIVE;
    }


    @Override
    public int setCooldown() {
        return 0;
    }

    @Override
    public ArrayList<String> setLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+"Your melee hits deal"+ ChatColor.DARK_RED +" 60%"+ChatColor.GRAY+ " less damage");
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
        if (e.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) return;
        RegistryEntity entity = RegistryEntity.getRegistryEntity(e.getEntity());
        if (entity == null) return;
        e.setDamage(e.getDamage()*0.4);
    }

    @Override
    public void ability(MagicHitEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(EntityDeathEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(EntityShootBowEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(ProjectileHitEvent e, RegistryPlayer player) {

    }
}
