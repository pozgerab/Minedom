package com.gertoxq.minedom.registry.item.items;

import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.abilities.Lightning;
import com.gertoxq.minedom.registry.item.AbilityItem;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class Slayer extends AbilityItem {

    public Slayer() {
        super();
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public HashMap<Stats, Double> getStats() {
        return Stats.newPlayerStats(0.0, 60.0, 30.0, 10.0, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0);
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
    public ArrayList<Ability> getAbilities() {
        ArrayList<Ability> abilities = new ArrayList<>();
        abilities.add(new Lightning());
        return abilities;
    }

}
