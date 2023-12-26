package com.gertoxq.minedom.registry.ability.interfaces.actionfaces;

import com.gertoxq.minedom.events.Custom.Events.RegistryHit.RegistryHitEvent;
import com.gertoxq.minedom.registry.entity.RegistryEntity;

public interface DamagerAction {

    /**
     * @return The base ability action damage
     */
    double getDamage();

    /**
     * @return Source of the damage
     */
    RegistryHitEvent.DamageSource getDamageType();

    /**
     * Use this to do the damage
     * @param entity Damager player
     * @param victim Hurt entity
     */
    default void doDamage(RegistryEntity entity, RegistryEntity victim) {
        victim.damage(getDamage(), entity, getDamageType());
    }

}
