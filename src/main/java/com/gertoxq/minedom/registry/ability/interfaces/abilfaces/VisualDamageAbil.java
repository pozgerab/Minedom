package com.gertoxq.minedom.registry.ability.interfaces.abilfaces;
import com.gertoxq.minedom.registry.ability.interfaces.actionfaces.DamagerAction;

public interface VisualDamageAbil {

    /**
     * ONLY VISUAL. Use {@link DamagerAction} on an action to set the actual damage
     * @return The damage displayed in the ability lore.
     */
    double getDamage();
}
