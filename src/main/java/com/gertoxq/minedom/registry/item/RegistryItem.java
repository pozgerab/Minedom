package com.gertoxq.minedom.registry.item;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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
    public ArrayList<String> statLore = new ArrayList<>();
    public ArrayList<String> abilityLore;
    public ArrayList<String> allLore = new ArrayList<>();
    public ItemMeta meta;
    public int uuid;
    public String name;
    public HashMap<Stats, Double> stats;
    public Material material;
    public ArrayList<Ability> abilities;
    public Boolean hasRequirement;
    public Skill requirementType;
    public int requirementLvl;

    public RegistryItem() {
        this.item = null;
    }

    public abstract Material setMaterial();

    public abstract HashMap<Stats, Double> setStats();

    public abstract String setName();

    public abstract String setID();

    public abstract ArrayList<String> setLore();

    public abstract ItemMeta setMeta();

    public abstract ArrayList<Ability> setAbilities();

    public abstract Boolean hasRequirement();

    public abstract Skill setRequirementType();

    public abstract int setRequirementLvl();

    public ItemStack register() {
        this.uuid = UUIDAmount;
        UUIDAmount++;
        this.id = setID();
        this.stats = setStats();
        this.material = setMaterial();
        this.item = new ItemStack(setMaterial(), 1);
        this.name = setName();
        this.abilities = setAbilities();
        this.hasRequirement = hasRequirement();
        this.requirementType = setRequirementType();
        this.requirementLvl = setRequirementLvl();
        item = new ItemStack(setMaterial());
        if (setMeta() == null) {meta = item.getItemMeta();} else {meta = setMeta();}
        if (setLore() == null) {lore = new ArrayList<>();} else {lore = setLore();}
        this.abilityLore = new ArrayList<>();
        for (Ability ability : setAbilities()) {
            abilityLore.addAll(ability.lore);
        }
        for (Stats stat : Stats.values()) {
            if (stats.get(stat) == 0) continue;
            statLore.add(ChatColor.GRAY+stat.displayName+": "+stats.get(stat));
        }
        allLore.add("");
        allLore.addAll(statLore);
        allLore.add("");
        allLore.addAll(abilityLore);
        allLore.add("");
        allLore.addAll(lore);
        meta.setLore(allLore);
        meta.setDisplayName(setName());
        meta.getPersistentDataContainer().set(new NamespacedKey(Minedom.getPlugin(), "itemID"), PersistentDataType.INTEGER, uuid);
        item.setItemMeta(meta);
        registryItems.put(setID(), this);
        return item;
    }

    public static RegistryItem getItemByItemStack(ItemStack itemFrom) {
        if (itemFrom == null || itemFrom.getItemMeta() == null || itemFrom.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Minedom.getPlugin(), "itemID"), PersistentDataType.INTEGER) == null) return null;
        return registryItems.values().stream().filter(item -> item.uuid == itemFrom.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Minedom.getPlugin(), "itemID"), PersistentDataType.INTEGER)).findAny().orElse(null);
    }

    public static RegistryItem getItemById(String id) {
        return registryItems.values().stream().filter(item -> Objects.equals(item.id, id)).findAny().orElse(null);
    }

    public static HashMap<String, RegistryItem> getRegistryItems() {
        return registryItems;
    }

}
