package com.gertoxq.minedom.registry.ability.action;

import com.gertoxq.minedom.registry.RegistryPlayer;

/**
 * Used to add state to {@link AbilityAction}s. Whenever the action is active
 * and the abilities change, the {@link #cleanUp(RegistryPlayer player)} will run on the player.
 */
public interface Statable {
    /**
     * Executes when the ability becomes inactive
     * @param player Player to clear
     */
    void cleanUp(RegistryPlayer player);
}
