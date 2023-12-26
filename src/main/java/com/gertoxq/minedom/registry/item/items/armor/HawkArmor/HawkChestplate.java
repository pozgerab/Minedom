package com.gertoxq.minedom.registry.item.items.armor.HawkArmor;

import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.abilities.Hawkeye;
import com.gertoxq.minedom.registry.item.AbilityItem;
import com.gertoxq.minedom.util.StatContainter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HawkChestplate extends AbilityItem {
    @Override
    public Material getMaterial() {
        return Material.CHAINMAIL_CHESTPLATE;
    }

    @Override
    public StatContainter getStats() {
        StatContainter containter = new StatContainter();
        containter.setHEALTH(80);
        containter.setDEFENSE(10);
        containter.setDAMAGE(10);
        containter.setSTRENGTH(10);
        containter.setAGILITY(20);
        return containter;
    }

    @Override
    public String getName() {
        return ChatColor.RESET+""+ChatColor.WHITE+"Hawk Chestplate";
    }

    @Override
    public String getID() {
        return "hawk_chestplate";
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
        ArrayList<Ability> abilities = new ArrayList<>();
        abilities.add(new Hawkeye());
        return abilities;
    }

}
