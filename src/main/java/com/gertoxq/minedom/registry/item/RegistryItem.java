package com.gertoxq.minedom.registry.item;

import com.gertoxq.minedom.Minedom;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

/**
 * Custom item
 */
public abstract class RegistryItem {

    /**
     * Stored all registered items by their ids
     */
    public static final HashMap<String, RegistryItem> registryItems = new HashMap<>();
    /**
     * A counter that gives each item a unique number id
     */
    public static int UUIDAmount = 0;
    /**
     * Unique identifier
     */
    public String id;
    /**
     * Bukkit item stack
     */
    public ItemStack item;
    /**
     * Item lore
     */
    public ArrayList<String> lore;
    /**
     * All lore combined (edit this field in the subclasses to add additional lore)
     */
    public ArrayList<String> allLore = new ArrayList<>();
    /**
     * Item's item meta
     */
    public ItemMeta meta;
    /**
     * Unique number id
     */
    public int uuid;
    /**
     * Display name
     */
    public String name;
    /**
     * Item material
     */
    public Material material;

    /**
     * Initiates the item
     */
    public RegistryItem() {
        this.item = new ItemStack(getMaterial(), 1);
        this.uuid = UUIDAmount;
        UUIDAmount++;
        this.id = getID();
        this.material = getMaterial();
        this.name = getName();
        item = new ItemStack(getMaterial());
        meta = getMeta() != null ? getMeta() : item.getItemMeta();
        lore = getLore() != null ? getLore() : new ArrayList<>();
        if (getLore() != null) allLore.add("");
        allLore.addAll(lore);
        meta.setLore(allLore);
        meta.setDisplayName(getName());
        meta.getPersistentDataContainer().set(new NamespacedKey(Minedom.getPlugin(), "itemID"), PersistentDataType.INTEGER, uuid);
        item.setItemMeta(meta);
        registryItems.put(getID(), this);
    }

    /**
     * @return Item's material
     */
    public abstract Material getMaterial();

    /**
     * @return Item's display name
     */
    public abstract String getName();

    /**
     * Unique item identifier
     * @return ID
     */
    public abstract String getID();

    /**
     * @return Basic item lore
     */
    public abstract ArrayList<String> getLore();

    /**
     * @return Item's item meta
     */
    public abstract ItemMeta getMeta();

    /**
     * Gets the registry item instance from Bukkit item stack
     * @param itemFrom Bukkit ItemStack
     * @return Registry item instance
     */
    public static RegistryItem getItemByItemStack(ItemStack itemFrom) {
        if (itemFrom == null || itemFrom.getItemMeta() == null || itemFrom.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Minedom.getPlugin(), "itemID"), PersistentDataType.INTEGER) == null) return null;
        return registryItems.values().stream().filter(item -> item.uuid == itemFrom.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Minedom.getPlugin(), "itemID"), PersistentDataType.INTEGER)).findAny().orElse(null);
    }

    /**
     * Gets the registry item instance by id
     * @param id Searched ID
     * @return registry item instance
     */
    public static RegistryItem getItemById(String id) {
        return registryItems.values().stream().filter(item -> Objects.equals(item.id, id)).findAny().orElse(null);
    }

}
