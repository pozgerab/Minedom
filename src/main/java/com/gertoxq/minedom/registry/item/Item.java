package com.gertoxq.minedom.registry.item;

import com.gertoxq.minedom.Minedom;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Objects;

public interface Item {
    /**
     * @return Item's material
     */
    Material getMaterial();

    /**
     * @return Item's display name
     */
    String getName();

    /**
     * Unique item identifier
     * @return ID
     */
    String getID();

    /**
     * @return Basic item lore
     */
    ArrayList<String> getLore();

    /**
     * @return Item's item meta
     */
    ItemMeta getMeta();
}
