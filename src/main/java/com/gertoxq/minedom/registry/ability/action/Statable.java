package com.gertoxq.minedom.registry.ability.action;

import com.gertoxq.minedom.registry.RegistryPlayer;

/**
 * Used to add state to {@link AbilityAction}s. Whenever the action is active and the abilities get refreshed, the {@link #cleanUp(RegistryPlayer player)} will run on the player.
 */
public interface Statable {
    /**
     * Executes when the ability becomes inactive
     * @param player
     */
    void cleanUp(RegistryPlayer player);
}
