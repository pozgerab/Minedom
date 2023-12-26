package com.gertoxq.minedom.registry.item;

import com.gertoxq.minedom.registry.ability.ItemAbility;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Item that has full set ability
 */
public abstract class FullsetAbilityItem extends AbilityItem {
    /**
     * Identifier shared with other items in the full armor set
     */
    public String groupId;
    /**
     * Full set abilities
     */
    public ArrayList<ItemAbility> fullSetAbilities;
    /**
     * Full set abilities' lore
     */
    public ArrayList<String> fullSetAbilityLore;

    /**
     * Gets the group id of the item
     * @return Group id
     */
    public abstract String getGroupId();

    /**
     * Gets the full set abilities
     * @return Item's full set abilities
     */
    public abstract ArrayList<ItemAbility> getFullSetAbilities();

    /**
     * Item constructor
     */
    public FullsetAbilityItem() {
        super();
        this.fullSetAbilities = getFullSetAbilities();
        this.groupId = getGroupId();
        this.fullSetAbilityLore = new ArrayList<>();
        if (getFullSetAbilities() != null) {
            for (ItemAbility ability : getFullSetAbilities()) {
                fullSetAbilityLore.addAll(ability.lore);
            }
            allLore.add("");
            allLore.addAll(fullSetAbilityLore);
        }
        meta.setLore(allLore);
        item.setItemMeta(meta);
    }

    /**
     * Gets full set ability item by Bukkit item stack
     * @param itemStack Bukkit item stack
     * @return Found full set ability item | null
     */
    @Nullable
    public static FullsetAbilityItem getFullsetAbilityItemByItemStack(ItemStack itemStack) {
        RegistryItem item = getItemByItemStack(itemStack);
        if (item instanceof FullsetAbilityItem abilityItem) return abilityItem;
        return null;
    }
}
