package com.gertoxq.minedom.registry.ability.interfaces.abilfaces;

import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;

public interface RequirementAbil {

    /**
     * @return The requirement skill type
     */
    Skill getRequirementType();

    /**
     * @return  The required skill level of the required skill type
     */
    int getRequirementLevel();

    default boolean condition(RegistryPlayer player) {
        return !(player.skillLevels.get(getRequirementType()) < getRequirementLevel());
    }

}
