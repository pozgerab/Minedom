package com.gertoxq.minedom.registry.item.items;

import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.abilities.lightning.Lightning;
import com.gertoxq.minedom.registry.item.AbilityItem;
import com.gertoxq.minedom.util.StatContainter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Slayer extends AbilityItem {

    public Slayer() {
        super();
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public StatContainter getStats() {
        StatContainter containter = new StatContainter();
        containter.setDEFENSE(60);
        containter.setDAMAGE(30);
        containter.setAGILITY(100);
        return containter;
    }

    @Override
    public String getName() {
        return ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "Slayer";
    }

    @Override
    public String getID() {
        return "slayer";
    }

    @Override
    public ArrayList<String> getLore() {
        return null;
    }

    @Override
    public ItemMeta getMeta() {
        ItemMeta meta = new ItemStack(Material.DIAMOND_SWORD).getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier("speed", 16, AttributeModifier.Operation.ADD_NUMBER));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        return meta;
    }

    @Override
    public List<Ability> getAbilities() {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(new Lightning());
        return abilities;
    }

}
