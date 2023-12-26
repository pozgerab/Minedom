package com.gertoxq.minedom.registry.item.items.armor.BeastArmor;

import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.ItemAbility;
import com.gertoxq.minedom.registry.ability.abilities.devour.Devour;
import com.gertoxq.minedom.registry.item.FullsetAbilityItem;
import com.gertoxq.minedom.util.StatContainter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BeastBoots extends FullsetAbilityItem {

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_BOOTS;
    }

    @Override
    public StatContainter getStats() {
        StatContainter containter = new StatContainter();
        containter.setHEALTH(60);
        containter.setDEFENSE(-100);
        containter.setSTRENGTH(10);
        containter.setMANA(50);
        containter.setAGILITY(20);
        return containter;
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
    public List<Ability> getAbilities() {
        return null;
    }

    @Override
    public String getGroupId() {
        return "beast";
    }

    @Override
    public ArrayList<ItemAbility> getFullSetAbilities() {
        ArrayList<ItemAbility> abilities1 = new ArrayList<>();
        abilities1.add(new Devour());
        return abilities1;
    }
}
