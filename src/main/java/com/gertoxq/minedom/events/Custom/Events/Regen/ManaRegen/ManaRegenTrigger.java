package com.gertoxq.minedom.events.Custom.Events.Regen.ManaRegen;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.gertoxq.minedom.events.PlayerStatSetup;

public class ManaRegenTrigger {


    /**
     * Have to be initiated somewhere, now in the {@link PlayerStatSetup#setUp(Player)}
     * @param player
     */
    public ManaRegenTrigger(RegistryPlayer player) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Minedom.getPlugin(), () -> {
            if (player.manapool != player.stats.getMANA()) new ManaRegenEvent(player, 1).callEvent();
        }, 0, 10);
    }

}
