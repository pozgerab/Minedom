package com.gertoxq.minedom.registry.item.items.armor.BeastArmor;

import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.abilities.Devour;
import com.gertoxq.minedom.registry.item.FullsetAbilityItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class BeastLeggings extends FullsetAbilityItem {
    @Override
    public Material setMaterial() {
        return Material.DIAMOND_LEGGINGS;
    }

    @Override
    public HashMap<Stats, Double> setStats() {
        return Stats.newPlayerStats(100.0, -100.0, 0.0, 10.0, 50.0, 0.0, 0.0, 0.0, 0.0, 20.0, 0.0);
    }

    @Override
    public String setName() {
        return ChatColor.RESET + "" + ChatColor.DARK_RED + "Beast Leggings";
    }

    @Override
    public String setID() {
        return "beast_leggings";
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
        return null;
    }

    @Override
    public String setGroupID() {
        return "beast";
    }

    @Override
    public ArrayList<Ability> setFullSetAbilities() {
        ArrayList<Ability> abilities1 = new ArrayList<>();
        abilities1.add(new Devour());
        return abilities1;
    }
}
