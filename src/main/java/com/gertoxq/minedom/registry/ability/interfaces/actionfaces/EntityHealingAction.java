package com.gertoxq.minedom.registry.ability.interfaces.actionfaces;

import com.gertoxq.minedom.registry.entity.RegistryEntity;

public interface EntityHealingAction extends StaticHealingAction {

    /**
     * Executes the healing
     * @param healed Entity healed
     * @param healFrom Healer entity
     */
    default void doHealing(RegistryEntity healed, RegistryEntity healFrom) {
        healed.healByEntity(getHealAmount(), healFrom);
    }
}
