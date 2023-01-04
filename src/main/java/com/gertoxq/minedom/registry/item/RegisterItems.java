package com.gertoxq.minedom.registry.item;

import com.gertoxq.minedom.registry.item.items.Slayer;
import com.gertoxq.minedom.registry.item.items.SoulCountrol;
import com.gertoxq.minedom.registry.item.items.armor.BeastArmor.BeastChestplate;

public class RegisterItems {

    public RegisterItems() {
        new Slayer().register();
        new BeastChestplate().register();
        new SoulCountrol().register();
    }

}
