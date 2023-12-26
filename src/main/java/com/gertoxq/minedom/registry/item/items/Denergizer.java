package com.gertoxq.minedom.registry.item.items;

import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.abilities.denergize.Denergize;
import com.gertoxq.minedom.registry.item.AbilityItem;
import com.gertoxq.minedom.util.StatContainter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Denergizer extends AbilityItem {
    @Override
    public List<Ability> getAbilities() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(new Denergize());
        return abilities;
    }

    @Override
    public Material getMaterial() {
        return Material.WOODEN_SWORD;
    }

    @Override
    public String getName() {
        return ChatColor.AQUA+"Denergizer";
    }

    @Override
    public String getID() {
        return "denergizer";
    }

    @Override
    public ArrayList<String> getLore() {
        return new ArrayList<>();
    }

    @Override
    public ItemMeta getMeta() {
        return null;
    }

    @Override
    public StatContainter getStats() {
        StatContainter containter = new StatContainter();
        containter.setDAMAGE(20);
        return containter;
    }
}
