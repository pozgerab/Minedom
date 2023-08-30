package com.gertoxq.minedom.registry.ability.action;

import com.gertoxq.minedom.registry.player.RegistryPlayer;

public interface Statable {
    /**
     * Executes when the ability becomes inactive
     * @param player
     */
    void cleanUp(RegistryPlayer player);
}
