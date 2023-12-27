package com.gertoxq.minedom.events.Custom;

import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import com.gertoxq.minedom.registry.ability.ItemAbility;
import com.gertoxq.minedom.events.AbilityListeners.PublicAbilityListener;

/**
 * Used to make a custom event trigger abilities
 * Registration needed in {@link ItemAbility} and {@link PublicAbilityListener}
 */
public interface AEvent {
    /**
     * @return The ability interface (event) that is responsible for running the ability
     */
    @NotNull
    Class<? extends AbilityInterface> getTriggerFace();
}
