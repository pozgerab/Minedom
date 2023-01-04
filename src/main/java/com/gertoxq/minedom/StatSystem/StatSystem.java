package com.gertoxq.minedom.StatSystem;

import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.registry.player.RegistryPlayer;

import java.util.HashMap;

public class StatSystem {


    public static HashMap<Stats, Double> newPassiveStats(Double hp, Double def) {
        return newPlayerStats(hp,def,0.0,0.0,0.0,0.0,0.0,0.0, 0.0);
    }

    public static HashMap<Stats, Double> newActiveStats(Double hp, Double def, Double damage, Double magicDef) {
        return newPlayerStats(hp,def,damage,0.0,0.0,0.0,magicDef,0.0, 0.0);
    }

    public static HashMap<Stats, Double> newPlayerStats(Double hp, Double def, Double damage, Double strength, Double mana, Double magicDmg, Double magicDef, Double healing, Double agility) {
        HashMap<Stats, Double> stats = new HashMap<>();
        stats.put(Stats.HEALTH, hp);
        stats.put(Stats.DEFENSE, def);
        stats.put(Stats.DAMAGE, damage);
        stats.put(Stats.STRENGTH, strength);
        stats.put(Stats.MANA, mana);
        stats.put(Stats.MAGIC_DAMAGE, magicDmg);
        stats.put(Stats.MAGIC_DEFENSE, magicDef);
        stats.put(Stats.HEALING, healing);
        stats.put(Stats.AGILITY,agility);
        return stats;
    }

    public static HashMap<Stats, Double> newEmptyPlayerStats() {
        return newPlayerStats(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0);
    }

    public static void addItemStat(HashMap<Stats, Double> statMap, RegistryItem item) {
        for (Stats stat : Stats.values()) {
            statMap.put(stat, statMap.get(stat) + item.stats.get(stat));
        }
    }

    public static void addStats(HashMap<Stats, Double> statMap, HashMap<Stats, Double> anotherMap) {
        for (Stats stat : anotherMap.keySet()) {
            statMap.put(stat, statMap.get(stat) + anotherMap.get(stat));
        }
    }

    public static HashMap<Stats, Double> sumStats(RegistryPlayer player) {
        HashMap<Stats, Double> statMap = new HashMap<>();
        for (Stats stat : player.equipmentStats.keySet()) {
            statMap.put(stat, player.equipmentStats.get(stat) + player.abilityStats.get(stat) + player.profileStats.get(stat));
        }
        return statMap;
    }

}
