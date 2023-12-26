package com.gertoxq.minedom.registry.ability.interfaces.actionfaces;

import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.registry.entity.RegistryEntity;

public interface StatBuffAction {

    /**
     * Applies Buff/Nerf
     * @param buffed Buffed entity
     * @param stat Stat
     * @param amount Buff amount
     * @param time Buff duration
     */
    default void applyBuff(RegistryEntity buffed, Stat stat, double amount, long time) {
        (buffed instanceof RegistryPlayer player ? player.abilityStats : buffed.stats).increaseForTick(stat, amount, time);
    }

}
