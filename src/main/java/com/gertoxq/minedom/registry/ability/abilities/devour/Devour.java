package com.gertoxq.minedom.registry.ability.abilities.devour;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryDeathEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.TriggerFace.*;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;

public class Devour extends Ability implements DeathAbility, InitAbility {

    @Override
    public String getName() {
        return "Devour";
    }

    @Override
    public String getId() {
        return "devour";
    }

    @Override
    public double getBaseDamage() {
        return 0.0;
    }

    @Override
    public AbilityState getState() {
        return AbilityState.FULL_ARMOR;
    }

    @Override
    public int getCooldown() {
        return 12;
    }

    @Override
    public TriggerType getTriggerType() {
        return TriggerType.FULL_ARMOR;
    }

    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "On killing an enemy, get 20 " + Stats.AGILITY.displayName);
        lore.add(ChatColor.GRAY + "and 10 " + Stats.STRENGTH.displayName + " for 4 seconds.");
        lore.add(ChatColor.GRAY + "On expiring, the lost buffs get");
        lore.add(ChatColor.GRAY + "converted to absorption for 10s.");
        lore.add(ChatColor.DARK_GRAY + "(Buffs and absorption stack)");
        return lore;
    }

    @Override
    public boolean getHasRequirement() {
        return false;
    }

    @Override
    public Skill getRequirementType() {
        return null;
    }

    @Override
    public int getRequirementLevel() {
        return 0;
    }

    @Override
    public AbilityAction ability(DeathAbility classs) {
        return KillBuff.action;
    }

    @Override
    public AbilityAction ability(InitAbility classs) {
        return new Halo();
    }
}
