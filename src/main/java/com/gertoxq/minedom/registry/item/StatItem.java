package com.gertoxq.minedom.registry.item;

import com.gertoxq.minedom.StatSystem.Stats;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class StatItem extends RegistryItem {


    public HashMap<Stats, Double> stats;
    public ArrayList<String> statLore = new ArrayList<>();

    public abstract HashMap<Stats, Double> setStats();

    public StatItem() {
        super();
        this.stats = setStats();
        if (setStats() != null) for (Stats stat : Stats.values()) {
            if (stats.get(stat) == 0) continue;
            statLore.add(ChatColor.GRAY+stat.displayName+": "+stats.get(stat));
        }
        allLore.add("");
        allLore.addAll(statLore);
        meta.setLore(allLore);
        item.setItemMeta(meta);
    }

    public static StatItem getStatItemByItemStack(ItemStack itemStack) {
        RegistryItem item = getItemByItemStack(itemStack);
        if (item instanceof StatItem statItem) return statItem;
        return null;
    }
}
