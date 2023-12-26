package com.gertoxq.minedom.registry.item;

import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.ItemAbility;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Item that has abilities
 */
public abstract class AbilityItem extends StatItem {

    /**
     * Item abilities' lore
     */
    public ArrayList<String> abilityLore;
    /**
     * Item abilities
     */
    public List<Ability> abilities;

    /**
     * Get item abilities
     *
     * @return List of ability
     */
    public abstract List<Ability> getAbilities();

    /**
     * Ability item constructor
     */
    public AbilityItem() {
        super();
        this.abilities = getAbilities();
        this.abilityLore = new ArrayList<>();
        if (getAbilities() != null) {
            allLore.addAll(getAbilities().get(0).buildMultipleDesc(getAbilities()));
        }
        meta.setLore(allLore);
        item.setItemMeta(meta);
    }

    /**
     * Gets ability from item stack
     * @param itemStack Bukkit item stack
     * @return Ability found | null
     */
    @Nullable
    public static AbilityItem getAbilityItemByItemStack(ItemStack itemStack) {
        RegistryItem item = getItemByItemStack(itemStack);
        if (item instanceof AbilityItem abilityItem) return abilityItem;
        return null;
    }
}
