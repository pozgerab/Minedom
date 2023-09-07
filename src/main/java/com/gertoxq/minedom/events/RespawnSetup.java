package com.gertoxq.minedom.events;

import com.gertoxq.minedom.events.Custom.Events.RegistryDeathEvent;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Listens for deaths and respawns
 */
public class RespawnSetup implements Listener {
    /**
     * Listens for respawns and sets up player data
     * @param e {@link PlayerRespawnEvent}
     */
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, -1, 2, false, false, false));
        PlayerStatSetup.setUp(e.getPlayer());
    }

    /**
     * Listens for deaths and cleans player data
     * @param e {@link RegistryDeathEvent}
     */
    @EventHandler
    public void onDeath(RegistryDeathEvent e) {
        if (!(e.getEntity() instanceof RegistryPlayer player)) return;
        PlayerStatSetup.cleanUp(player.player);
    }
}
