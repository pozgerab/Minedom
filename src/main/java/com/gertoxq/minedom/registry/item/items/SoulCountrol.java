package com.gertoxq.minedom.registry.item.items;

import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.abilities.Marksman;
import com.gertoxq.minedom.registry.ability.abilities.Overwhelm;
import com.gertoxq.minedom.registry.item.AbilityItem;
import com.gertoxq.minedom.util.StatContainter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SoulCountrol extends AbilityItem {
    @Override
    public Material getMaterial() {
        return Material.CROSSBOW;
    }

    @Override
    public StatContainter getStats() {
        StatContainter containter = new StatContainter();
        containter.setDAMAGE(40);
        containter.setSTRENGTH(30);
        return containter;
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
    public List<Ability> getAbilities() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(new Marksman());
        abilities.add(new Overwhelm());
        return abilities;
    }
}
