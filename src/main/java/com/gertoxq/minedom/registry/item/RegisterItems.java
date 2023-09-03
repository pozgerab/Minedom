package com.gertoxq.minedom.registry.item;

import com.gertoxq.minedom.registry.item.items.CustomEgg;
import com.gertoxq.minedom.registry.item.items.Slayer;
import com.gertoxq.minedom.registry.item.items.SoulCountrol;
import com.gertoxq.minedom.registry.item.items.armor.BeastArmor.BeastBoots;
import com.gertoxq.minedom.registry.item.items.armor.BeastArmor.BeastChestplate;
import com.gertoxq.minedom.registry.item.items.armor.BeastArmor.BeastHelmet;
import com.gertoxq.minedom.registry.item.items.armor.BeastArmor.BeastLeggings;
import com.gertoxq.minedom.registry.item.items.armor.HawkArmor.HawkChestplate;

/**
 * Registers custom items
 */
public class RegisterItems {
    /**
     * Registers items. (Insert them in this method)
     */
    public RegisterItems() {
        new BeastBoots();
        new BeastLeggings();
        new BeastChestplate();
        new BeastHelmet();
        new HawkChestplate();
        new CustomEgg();
        new Slayer();
        new SoulCountrol();
    }
}
