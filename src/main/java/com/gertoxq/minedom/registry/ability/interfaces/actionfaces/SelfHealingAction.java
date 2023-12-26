package com.gertoxq.minedom.registry.ability.interfaces.actionfaces;

import com.gertoxq.minedom.events.Custom.Events.Regen.RegenEvent;
import com.gertoxq.minedom.registry.entity.RegistryEntity;

public interface SelfHealingAction extends StaticHealingAction {

    /**
     * Executes healing
     * @param healed Healed entity
     */
    default void doHealing(RegistryEntity healed) {
        healed.heal(getHealAmount(), RegenEvent.RegenCause.MAGIC);
    }
}
