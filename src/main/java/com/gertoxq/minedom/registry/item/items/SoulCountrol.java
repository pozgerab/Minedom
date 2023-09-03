package com.gertoxq.minedom.registry.item.items;

import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.abilities.Marksman;
import com.gertoxq.minedom.registry.ability.abilities.Overwhelm;
import com.gertoxq.minedom.registry.item.AbilityItem;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.registry.item.StatItem;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class SoulCountrol extends AbilityItem {
    @Override
    public Material getMaterial() {
        return Material.CROSSBOW;
    }

    @Override
    public HashMap<Stats, Double> getStats() {
        return Stats.newPlayerStats(0.0,0.0,40.0,30.0,0.0,0.0,0.0,0.0, 0.0,0.0, 0.0);
    }

    @Override
    public String getName() {
        return ChatColor.BLUE+"Soul Control";
    }

    @Override
    public String getID() {
        return "soul_control";
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
        ArrayList<Ability> abilities = new ArrayList<>();
        abilities.add(new Marksman());
        abilities.add(new Overwhelm());
        return abilities;
    }
}
