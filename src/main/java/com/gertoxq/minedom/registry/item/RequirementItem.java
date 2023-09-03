package com.gertoxq.minedom.registry.item;

import com.gertoxq.minedom.skill.Skill;
import org.bukkit.ChatColor;

import java.util.ArrayList;

/**
 * Item with requirement
 */
public abstract class RequirementItem extends AbilityItem {

    /**
     * Required skill type
     */
    public Skill requirementType;
    /**
     * Required skill level
     */
    public int requirementLvl;
    /**
     * Lore generated and added to {@link #allLore} to show requirement
     */
    public ArrayList<String> reqLore = new ArrayList<>();

    /**
     * Init, adds lore to {@link #allLore}
     */
    public RequirementItem() {
        super();
        this.requirementType = getRequirementType();
        this.requirementLvl = getRequirementLvl();
        reqLore.add(ChatColor.GRAY+"Requires Level "+requirementLvl+" in " + requirementType.displayName);
        allLore.addAll(reqLore);
        allLore.add("");
        meta.setLore(allLore);
        item.setItemMeta(meta);
    }

    /**
     * @return Item's required skill type
     */
    public abstract Skill getRequirementType();

    /**
     * @return Item's required skill level
     */
    public abstract int getRequirementLvl();
}
