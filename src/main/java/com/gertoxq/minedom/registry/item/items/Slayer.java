package com.gertoxq.minedom.registry.item.items;

import com.gertoxq.minedom.StatSystem.StatSystem;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.abilities.Lightning;
import com.gertoxq.minedom.registry.ability.abilities.Overwhelm;
import com.gertoxq.minedom.registry.ability.abilities.QuickShot;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class Slayer extends RegistryItem {

    public Slayer() {
        super();
    }

    @Override
    public Material setMaterial() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public HashMap<Stats, Double> setStats() {
        return StatSystem.newPlayerStats(0.0, 60.0, 30.0, 10.0, 0.0, 0.0, 0.0, 0.0, 100.0);
    }

    @Override
    public String setName() {
        return ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "Slayer";
    }

    @Override
    public String setID() {
        return "slayer";
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
        abilities.add(new Lightning());
        return abilities;
    }


    @Override
    public Boolean hasRequirement() {
        return false;
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
