package com.gertoxq.minedom.registry.item.items;

import com.gertoxq.minedom.StatSystem.StatSystem;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.abilities.Marksman;
import com.gertoxq.minedom.registry.ability.abilities.Overwhelm;
import com.gertoxq.minedom.registry.ability.abilities.QuickShot;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class SoulCountrol extends RegistryItem {
    @Override
    public Material setMaterial() {
        return Material.CROSSBOW;
    }

    @Override
    public HashMap<Stats, Double> setStats() {
        return StatSystem.newPlayerStats(0.0,0.0,40.0,30.0,0.0,0.0,0.0,0.0,0.0);
    }

    @Override
    public String setName() {
        return ChatColor.BLUE+"Soul Control";
    }

    @Override
    public String setID() {
        return "soul_control";
    }

    @Override
    public ArrayList<String> setLore() {
        return null;
    }

    @Override
    public ItemMeta setMeta() {
        return null;
    }

    @Override
    public ArrayList<Ability> setAbilities() {
        ArrayList<Ability> abilities = new ArrayList<>();
        abilities.add(new Marksman());
        abilities.add(new Overwhelm());
        return abilities;
    }

    @Override
    public Boolean hasRequirement() {
        return null;
    }

    @Override
    public Skill setRequirementType() {
        return null;
    }

    @Override
    public int setRequirementLvl() {
        return 0;
    }
}
