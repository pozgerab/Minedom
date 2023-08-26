package com.gertoxq.minedom.registry.item;

import com.gertoxq.minedom.skill.Skill;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class RequirementItem extends AbilityItem {

    public Skill requirementType;
    public int requirementLvl;
    public ArrayList<String> reqLore = new ArrayList<>();

    public RequirementItem() {
        super();
        this.requirementType = setRequirementType();
        this.requirementLvl = setRequirementLvl();
        reqLore.add(ChatColor.GRAY+"Requires Level "+requirementLvl+" in " + requirementType.displayName);
        allLore.addAll(reqLore);
        allLore.add("");
        meta.setLore(allLore);
        item.setItemMeta(meta);
    }

    public abstract Skill setRequirementType();

    public abstract int setRequirementLvl();
}
