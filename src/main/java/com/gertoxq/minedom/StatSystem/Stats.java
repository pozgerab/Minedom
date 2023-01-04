package com.gertoxq.minedom.StatSystem;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Objects;

public enum Stats {

    DAMAGE("damage", ChatColor.BLUE+"Damage"+ChatColor.GRAY, Material.DIAMOND_SWORD, 0),
    STRENGTH("strength", ChatColor.DARK_RED+"₪ Strength"+ChatColor.GRAY, Material.REDSTONE_BLOCK, 1),
    MANA("mana", ChatColor.DARK_AQUA+"Mana"+ChatColor.GRAY, Material.LAPIS_LAZULI, 2),
    MAGIC_DAMAGE("magic_damage", ChatColor.LIGHT_PURPLE+"Magic Damage"+ChatColor.GRAY, Material.TNT, 3),
    DEFENSE("defense", ChatColor.GREEN+"Defense"+ChatColor.GRAY, Material.NETHERITE_CHESTPLATE, 4),
    MAGIC_DEFENSE("magic_defense", ChatColor.BLACK+"Magic Defense"+ChatColor.GRAY, Material.TOTEM_OF_UNDYING, 5),
    HEALTH("health", ChatColor.RED+"Health"+ChatColor.GRAY, Material.APPLE, 6),
    HEALING("healing", ChatColor.GOLD+"Healing"+ChatColor.GRAY, Material.ENCHANTED_GOLDEN_APPLE, 7),
    AGILITY("agility", ChatColor.WHITE + "✦ Agility"+ChatColor.GRAY, Material.FEATHER, 8);

    public final String name;
    public final String displayName;
    public final Material asItem;
    public final int id;
    Stats(String name, String displayName, Material material, int id) {
        this.name = name;
        this.displayName = displayName;
        this.asItem = material;
        this.id = id;
    }

    public static Stats getStatFromName(String query) {
        return Arrays.stream(Stats.values()).filter(stat -> Objects.equals(stat.name, query)).findAny().orElse(null);
    }

    public static Stats getStatFromItem(Material query) {
        return Arrays.stream(Stats.values()).filter(stat -> Objects.equals(stat.asItem, query)).findAny().orElse(null);
    }

}