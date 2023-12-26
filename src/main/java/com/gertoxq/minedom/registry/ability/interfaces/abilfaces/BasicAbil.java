package com.gertoxq.minedom.registry.ability.interfaces.abilfaces;

import com.gertoxq.minedom.registry.ability.ItemAbility;

import java.util.ArrayList;

public interface BasicAbil {


    /**
     * @return The ability display name
     */
    String getName();
    /**
     * @return The lore of the ability
     */
    ArrayList<String> getLore();

    /**
     * @return The ability state
     */
    ItemAbility.AbilityState getState();
    /**
     * @return The unique id of the ability
     */
    String getId();

    /**
     * @return Ability trigger type
     */
    ItemAbility.TriggerType getTriggerType();

}
