package com.gertoxq.minedom.registry.item.items.armor.BeastArmor;

import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.abilities.devour.Devour;
import com.gertoxq.minedom.registry.item.FullsetAbilityItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class BeastBoots extends FullsetAbilityItem {

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_BOOTS;
    }

    @Override
    public HashMap<Stats, Double> getStats() {
        return Stats.newPlayerStats(60.0, -100.0, 0.0, 10.0, 50.0, 0.0, 0.0, 0.0, 0.0, 20.0, 0.0);
    }

    @Override
    public String getName() {
        return ChatColor.RESET + "" + ChatColor.DARK_RED + "Beast Boots";
    }

    @Override
    public String getID() {
        return "beast_boots";
    }

    @Override
    public ArrayList<String> getLore() {
        return null;
    }

    @Override
    public ItemMeta getMeta() {
        return null;
    }

    @Override
    public ArrayList<Ability> getAbilities() {
        return null;
    }

    @Override
    public String getGroupId() {
        return "beast";
    }

    @Override
    public ArrayList<Ability> getFullSetAbilities() {
        ArrayList<Ability> abilities1 = new ArrayList<>();
        abilities1.add(new Devour());
        return abilities1;
    }
}
