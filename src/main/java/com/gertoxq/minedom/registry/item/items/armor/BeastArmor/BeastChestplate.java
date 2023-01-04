package com.gertoxq.minedom.registry.item.items.armor.BeastArmor;

import com.gertoxq.minedom.StatSystem.StatSystem;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.abilities.Devour;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class BeastChestplate extends RegistryItem {

    @Override
    public Material setMaterial() {
        return Material.DIAMOND_CHESTPLATE;
    }

    @Override
    public HashMap<Stats, Double> setStats() {
        return StatSystem.newPlayerStats(100.0, 100.0, 0.0, 10.0, 50.0, 0.0, 0.0, 0.0, 20.0);
    }

    @Override
    public String setName() {
        return ChatColor.RESET + "" + ChatColor.DARK_RED + "Beast Chestplate";
    }

    @Override
    public String setID() {
        return "beast_chestplate";
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
        abilities.add(new Devour());
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
