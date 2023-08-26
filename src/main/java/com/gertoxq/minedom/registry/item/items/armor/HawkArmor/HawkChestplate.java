package com.gertoxq.minedom.registry.item.items.armor.HawkArmor;

import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.abilities.Hawkeye;
import com.gertoxq.minedom.registry.item.AbilityItem;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class HawkChestplate extends AbilityItem {
    @Override
    public Material setMaterial() {
        return Material.CHAINMAIL_CHESTPLATE;
    }

    @Override
    public HashMap<Stats, Double> setStats() {
        return Stats.newPlayerStats(80d, 10d, 10d, 10d, 0d, 0d, 0d, 0d, 0.0, 20d, 0.0);
    }

    @Override
    public String setName() {
        return ChatColor.RESET+""+ChatColor.WHITE+"Hawk Chestplate";
    }

    @Override
    public String setID() {
        return "hawk_chestplate";
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
        abilities.add(new Hawkeye());
        return abilities;
    }

}
