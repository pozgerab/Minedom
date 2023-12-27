package com.gertoxq.minedom.Stats;

import com.gertoxq.minedom.registry.item.StatItem;
import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.util.StatContainter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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
    public enum Stat {
    DAMAGE("damage", ChatColor.BLUE+"✗ Damage"+ChatColor.GRAY, Material.DIAMOND_SWORD, 0),
    STRENGTH("strength", ChatColor.DARK_RED+"₪ Strength"+ChatColor.GRAY, Material.BLAZE_POWDER, 1),
    MANA("mana", ChatColor.DARK_AQUA+"✿ Mana"+ChatColor.GRAY, Material.LAPIS_LAZULI, 2),
    MAGIC_DAMAGE("magic_damage", ChatColor.LIGHT_PURPLE+"☯ Magic Damage"+ChatColor.GRAY, Material.ENCHANTED_BOOK, 3),
    DEFENSE("defense", ChatColor.GREEN+"✤ Defense"+ChatColor.GRAY, Material.IRON_CHESTPLATE, 4),
    MAGIC_DEFENSE("magic_defense", ChatColor.GRAY+"ʊ Magic Defense"+ChatColor.GRAY, Material.TOTEM_OF_UNDYING, 5),
    HEALTH("health", ChatColor.RED+"❤ Health"+ChatColor.GRAY, Material.APPLE, 6),
    REGENERGY("regenergy", ChatColor.GOLD+"❈ Regenergy"+ChatColor.GRAY, Material.ENCHANTED_GOLDEN_APPLE, 7),
    VITALIS("vitalis", ChatColor.DARK_RED+"❉ Vitalis"+ChatColor.GRAY, Material.GLISTERING_MELON_SLICE, 8),
    AGILITY("agility", ChatColor.WHITE + "✦ Agility"+ChatColor.GRAY, Material.FEATHER, 9),
    BLESSING("blessing", ChatColor.AQUA+"α Blessing"+ChatColor.GRAY, Material.EXPERIENCE_BOTTLE, 10),
    MANA_REGEN("mana_regen", ChatColor.DARK_PURPLE+"ξ Mana Regen"+ChatColor.GRAY, Material.ENDER_EYE, 11),
    ATTACK_SPEED("attack_speed", ChatColor.DARK_AQUA+"կ Attack Speed"+ChatColor.GRAY, Material.SUGAR, 12);

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
    Stat(String name, String displayName, Material material, int id) {
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
    public static Stat getStatFromName(String query) {
        return Arrays.stream(Stat.values()).filter(stat -> Objects.equals(stat.name, query)).findAny().orElse(null);
    }

    /**
     * Get stat by representative item
     * @param query Material to search
     * @return Found stat | null
     */
    @Nullable
    public static Stat getStatFromItem(Material query) {
        return Arrays.stream(Stat.values()).filter(stat -> Objects.equals(stat.asItem, query)).findAny().orElse(null);
    }

    /**
     * @param hp Health
     * @param def Defense
     * @return New passive stat map
     */
    public static @NonNull StatContainter newPassiveStats(Double hp, Double def) {
        return newActiveStats(hp,def, 0.0, 0.0);
    }

    /**
     * @return New player stat map
     */
    public static StatContainter newPlayerStats(Double hp, Double def, Double damage, Double strength, Double mana, Double magicDmg, Double magicDef, Double healing, Double vitalis, Double agility, Double blessing, Double mana_regen, Double attack_speed) {
        StatContainter stats = new StatContainter();
        stats.put(Stat.HEALTH, hp);
        stats.put(Stat.DEFENSE, def);
        stats.put(Stat.DAMAGE, damage);
        stats.put(Stat.STRENGTH, strength);
        stats.put(Stat.MANA, mana);
        stats.put(Stat.MAGIC_DAMAGE, magicDmg);
        stats.put(Stat.MAGIC_DEFENSE, magicDef);
        stats.put(Stat.REGENERGY, healing);
        stats.put(VITALIS, vitalis);
        stats.put(Stat.AGILITY, agility);
        stats.put(BLESSING, blessing);
        stats.put(MANA_REGEN, mana_regen);
        stats.put(ATTACK_SPEED, attack_speed);
        return stats;
    }

    public static StatContainter newActiveStats(Double hp, Double def, Double damage, Double magicDef) {
        return newPlayerStats(hp,def,damage,0.0,0.0,0.0,magicDef,100.0, 100.0, 100.0, 100.0, 100.0, 0.0);
    }

    /**
     * @return New empty stat map
     */
    public static StatContainter newEmptyPlayerStats() {
        return newPlayerStats(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0, 0.0, 0.0, 0.0, 0.0);
    }

    /**
     * Adds an item's stats to a stat map
     * @param statMap Map to be added to
     * @param item Item to be added
     */
    public static void addItemStat(StatContainter statMap, StatItem item) {
        for (Stat stat : Stat.values()) {
            statMap.increase(stat, item.stats.get(stat));
        }
    }

    /**
     * @param level Regenergy stat level
     * @return How much seconds it takes to heal to full health from 0
     */
    private static double calcFullHealTime(double level) {
        return 20 / (0.01 * level + 1);
    }

    private static double calcHealPerHealing(/*double level, */double maxhp, double timeinsec) {
        //return maxhp / calcFullHealTime(level - 100) * 0.5;
        return maxhp / timeinsec * 0.5;
    }

    public static double calcPlayerHealOnce(RegistryPlayer player) {
        return calcHealPerHealing(player.stats.getHEALTH(), 20);
    }
    public static double calcPlayerHealManaOnce(RegistryPlayer player) {
        return calcHealPerHealing(player.stats.getMANA(), 20);
    }

    /**
     * Adds two stat map's values together
     * @param statMap map1
     * @param anotherMap map2
     */
    public static void addStats(StatContainter statMap, StatContainter anotherMap) {
        for (Stat stat : anotherMap.keySet()) {
            statMap.increase(stat, anotherMap.get(stat));
        }
    }

    /**
     * Sums up a player's stats
     * @param player Player to check
     * @return Summed up map
     */
    public static StatContainter sumStats(RegistryPlayer player) {
        StatContainter statMap = new StatContainter();
        for (Stat stat : Stat.values()) {
            statMap.put(stat, player.handStats.get(stat) + player.armorStats.get(stat) + player.abilityStats.get(stat) + player.profileStats.get(stat));
        }
        return statMap;
    }

}