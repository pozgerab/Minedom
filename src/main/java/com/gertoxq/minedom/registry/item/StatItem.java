package com.gertoxq.minedom.registry.item;

import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.util.StatContainter;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Item that holds stats
 */
public abstract class StatItem extends RegistryItem {


    /**
     * Stats that the item holds
     */
    public StatContainter stats;
    /**
     * Lore generated and added to {@link #allLore} to show stats
     */
    public ArrayList<String> statLore = new ArrayList<>();

    /**
     * @return Item's stats
     */
    public abstract StatContainter getStats();

    /**
     * Init, adds lore to {@link #allLore}
     */
    public StatItem() {
        super();
        this.stats = getStats();
        if (getStats() != null) for (Stat stat : Stat.values()) {
            if (stats.get(stat) == 0) continue;
            statLore.add(ChatColor.GRAY+stat.displayName+": "+stats.get(stat));
        }
        allLore.add("");
        allLore.addAll(statLore);
        meta.setLore(allLore);
        item.setItemMeta(meta);
    }

    /**
     * Gets stat item by item stack
     * @param itemStack Bukkit item stack
     * @return Found stat item | null
     */
    @Nullable
    public static StatItem getStatItemByItemStack(ItemStack itemStack) {
        RegistryItem item = getItemByItemStack(itemStack);
        if (item instanceof StatItem statItem) return statItem;
        return null;
    }
}
