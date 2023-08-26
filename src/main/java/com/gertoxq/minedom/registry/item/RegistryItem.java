package com.gertoxq.minedom.registry.item;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public abstract class RegistryItem {

    public static final HashMap<String, RegistryItem> registryItems = new HashMap<>();
    public static int UUIDAmount = 0;
    public String id;
    public ItemStack item;
    public ArrayList<String> lore = new ArrayList<>();
    public ArrayList<String> allLore = new ArrayList<>();
    public ItemMeta meta = setMeta() != null ? setMeta() : new ItemStack(Material.AIR).getItemMeta();
    public int uuid;
    public String name;
    public Material material;

    public RegistryItem() {
        this.item = new ItemStack(setMaterial(), 1);
        this.uuid = UUIDAmount;
        UUIDAmount++;
        this.id = setID();
        this.material = setMaterial();
        this.name = setName();
        item = new ItemStack(setMaterial());
        meta = setMeta() != null ? setMeta() : item.getItemMeta();
        lore = setLore() != null ? setLore() : new ArrayList<>();
        if (setLore() != null) allLore.add("");
        allLore.addAll(lore);
        meta.setLore(allLore);
        meta.setDisplayName(setName());
        meta.getPersistentDataContainer().set(new NamespacedKey(Minedom.getPlugin(), "itemID"), PersistentDataType.INTEGER, uuid);
        item.setItemMeta(meta);
        registryItems.put(setID(), this);
    }

    public abstract Material setMaterial();

    public abstract String setName();

    public abstract String setID();

    public abstract ArrayList<String> setLore();

    public abstract ItemMeta setMeta();

    public static RegistryItem getItemByItemStack(ItemStack itemFrom) {
        if (itemFrom == null || itemFrom.getItemMeta() == null || itemFrom.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Minedom.getPlugin(), "itemID"), PersistentDataType.INTEGER) == null) return null;
        return registryItems.values().stream().filter(item -> item.uuid == itemFrom.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Minedom.getPlugin(), "itemID"), PersistentDataType.INTEGER)).findAny().orElse(null);
    }

    public static RegistryItem getItemById(String id) {
        return registryItems.values().stream().filter(item -> Objects.equals(item.id, id)).findAny().orElse(null);
    }

}
