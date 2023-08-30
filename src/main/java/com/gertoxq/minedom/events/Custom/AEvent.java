package com.gertoxq.minedom.events.Custom;

import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import org.jetbrains.annotations.NotNull;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.events.AbilityListeners.PublicAbilityListener;

/**
 * Used to make a custom event trigger abilities
 * Registration needed in {@link Ability} and {@link PublicAbilityListener}
 */
public interface AEvent {

    @NotNull Class<? extends AbilityInterface> getTriggerFace();
}
