package com.gertoxq.minedom.registry.item;

import com.gertoxq.minedom.registry.ability.Ability;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class FullsetAbilityItem extends AbilityItem {

    public String groupId;
    public ArrayList<Ability> fullSetAbilities;
    public ArrayList<String> fullSetAbilityLore;


    public abstract String setGroupID();
    public abstract ArrayList<Ability> setFullSetAbilities();

    public FullsetAbilityItem() {
        super();
        this.fullSetAbilities = setFullSetAbilities();
        this.groupId = setGroupID();
        this.fullSetAbilityLore = new ArrayList<>();
        if (setFullSetAbilities() != null) {
            for (Ability ability : setFullSetAbilities()) {
                fullSetAbilityLore.addAll(ability.lore);
            }
            allLore.add("");
            allLore.addAll(fullSetAbilityLore);
        }
        meta.setLore(allLore);
        item.setItemMeta(meta);
    }

    public static FullsetAbilityItem getFullsetAbilityItemByItemStack(ItemStack itemStack) {
        RegistryItem item = getItemByItemStack(itemStack);
        if (item instanceof FullsetAbilityItem abilityItem) return abilityItem;
        return null;
    }
}
