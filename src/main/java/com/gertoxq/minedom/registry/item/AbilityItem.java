package com.gertoxq.minedom.registry.item;

import com.gertoxq.minedom.registry.ability.Ability;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class AbilityItem extends StatItem {

    public ArrayList<String> abilityLore;
    public ArrayList<Ability> abilities;

    public abstract ArrayList<Ability> setAbilities();

    public AbilityItem() {
        super();
        this.abilities = setAbilities();
        this.abilityLore = new ArrayList<>();
        if (setAbilities() != null) {
            for (Ability ability : setAbilities()) {
                abilityLore.addAll(ability.lore);
            }
            allLore.add("");
            allLore.addAll(abilityLore);
        }
        meta.setLore(allLore);
        item.setItemMeta(meta);
    }

    public static AbilityItem getAbilityItemByItemStack(ItemStack itemStack) {
        RegistryItem item = getItemByItemStack(itemStack);
        if (item instanceof AbilityItem abilityItem) return abilityItem;
        return null;
    }
}
