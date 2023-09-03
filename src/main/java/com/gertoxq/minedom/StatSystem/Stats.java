package com.gertoxq.minedom.StatSystem;

import com.gertoxq.minedom.registry.item.StatItem;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * List of stats
 */
public enum Stats {
    DAMAGE("damage", ChatColor.BLUE+"✗ Damage"+ChatColor.GRAY, Material.DIAMOND_SWORD, 0),
    STRENGTH("strength", ChatColor.DARK_RED+"₪ Strength"+ChatColor.GRAY, Material.REDSTONE_BLOCK, 1),
    MANA("mana", ChatColor.DARK_AQUA+"✿ Mana"+ChatColor.GRAY, Material.LAPIS_LAZULI, 2),
    MAGIC_DAMAGE("magic_damage", ChatColor.LIGHT_PURPLE+"☯ Magic Damage"+ChatColor.GRAY, Material.TNT, 3),
    DEFENSE("defense", ChatColor.GREEN+"✤ Defense"+ChatColor.GRAY, Material.NETHERITE_CHESTPLATE, 4),
    MAGIC_DEFENSE("magic_defense", ChatColor.BLACK+"ʊ Magic Defense"+ChatColor.GRAY, Material.TOTEM_OF_UNDYING, 5),
    HEALTH("health", ChatColor.RED+"❤ Health"+ChatColor.GRAY, Material.APPLE, 6),
    REGENERGY("regenergy", ChatColor.GOLD+"❈ Regenergy"+ChatColor.GRAY, Material.ENCHANTED_GOLDEN_APPLE, 7),
    VITALIS("vitalis", ChatColor.DARK_RED+"❉ Vitalis"+ChatColor.GRAY, Material.GLISTERING_MELON_SLICE, 8),
    AGILITY("agility", ChatColor.WHITE + "✦ Agility"+ChatColor.GRAY, Material.FEATHER, 9),
    BLESSING("blessing", ChatColor.AQUA+"α Blessing"+ChatColor.GRAY, Material.BEACON, 10);

    /**
     * Identifier of the stat
     */
    public final String name;
    /**
     * This is how the stat will be displayed in-game
     */
    public final String displayName;
    /**
     * Representative item
     */
    public final Material asItem;
    /**
     * Number identifier
     */
    public final int id;

    /**
     * Stat init
     * @param name Identifier
     * @param displayName Display name
     * @param material Representative material
     * @param id Number identifier
     */
    Stats(String name, String displayName, Material material, int id) {
        this.name = name;
        this.displayName = displayName;
        this.asItem = material;
        this.id = id;
    }

    /**
     * Gets stat by name
     * @param query Name to search
     * @return Stat found or null
     */
    @Nullable
    public static Stats getStatFromName(String query) {
        return Arrays.stream(Stats.values()).filter(stat -> Objects.equals(stat.name, query)).findAny().orElse(null);
    }

    /**
     * Get stat by representative item
     * @param query Material to search
     * @return Found stat | null
     */
    @Nullable
    public static Stats getStatFromItem(Material query) {
        return Arrays.stream(Stats.values()).filter(stat -> Objects.equals(stat.asItem, query)).findAny().orElse(null);
    }

    /**
     * @param hp Health
     * @param def Defense
     * @return New passive stat map
     */
    public static @NonNull HashMap<Stats, Double> newPassiveStats(Double hp, Double def) {
        return newPlayerStats(hp,def,0.0,0.0,0.0,0.0,0.0,0.0, 0.0, 0.0, 0.0);
    }

    /**
     * @return New player stat map
     */
    public static HashMap<Stats, Double> newPlayerStats(Double hp, Double def, Double damage, Double strength, Double mana, Double magicDmg, Double magicDef, Double healing, Double vitalis, Double agility, Double blessing) {
        HashMap<Stats, Double> stats = new HashMap<>();
        stats.put(Stats.HEALTH, hp);
        stats.put(Stats.DEFENSE, def);
        stats.put(Stats.DAMAGE, damage);
        stats.put(Stats.STRENGTH, strength);
        stats.put(Stats.MANA, mana);
        stats.put(Stats.MAGIC_DAMAGE, magicDmg);
        stats.put(Stats.MAGIC_DEFENSE, magicDef);
        stats.put(Stats.REGENERGY, healing);
        stats.put(VITALIS, vitalis);
        stats.put(Stats.AGILITY, agility);
        stats.put(BLESSING, blessing);
        return stats;
    }

    public static HashMap<Stats, Double> newActiveStats(Double hp, Double def, Double damage, Double magicDef) {
        return newPlayerStats(hp,def,damage,0.0,0.0,0.0,magicDef,0.0, 0.0, 0.0, 0.0);
    }

    /**
     * @return New empty stat map
     */
    public static HashMap<Stats, Double> newEmptyPlayerStats() {
        return newPlayerStats(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0, 0.0, 0.0);
    }

    /**
     * Adds an item's stats to a stat map
     * @param statMap Map to be added to
     * @param item Item to be added
     */
    public static void addItemStat(HashMap<Stats, Double> statMap, StatItem item) {
        for (Stats stat : Stats.values()) {
            statMap.put(stat, statMap.get(stat) + item.stats.get(stat));
        }
    }

    /**
     * @param level Regenergy stat level
     * @return How much seconds it takes to heal to full health from 0
     */
    private static double calcFullHealTime(double level) {
        return 20 / (0.01 * level + 1);
    }

    private static double calcHealPerHealing(double level, double maxhp) {
        return maxhp / calcFullHealTime(level - 100) * 0.5;
    }

    public static double calcPlayerHealOnce(RegistryPlayer player) {
        return calcHealPerHealing(player.stats.get(REGENERGY), player.stats.get(HEALTH));
    }

    /**
     * Adds two stat map's values together
     * @param statMap map1
     * @param anotherMap map2
     */
    public static void addStats(HashMap<Stats, Double> statMap, HashMap<Stats, Double> anotherMap) {
        for (Stats stat : anotherMap.keySet()) {
            statMap.put(stat, statMap.get(stat) + anotherMap.get(stat));
        }
    }

    /**
     * Sums up a player's stats
     * @param player Player to check
     * @return Summed up map
     */
    public static HashMap<Stats, Double> sumStats(RegistryPlayer player) {
        HashMap<Stats, Double> statMap = new HashMap<>();
        for (Stats stat : player.handStats.keySet()) {
            statMap.put(stat, player.handStats.get(stat) + player.armorStats.get(stat) + player.abilityStats.get(stat) + player.profileStats.get(stat));
        }
        return statMap;
    }

}