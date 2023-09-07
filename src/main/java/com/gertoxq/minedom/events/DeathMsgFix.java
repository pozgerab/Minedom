package com.gertoxq.minedom.events;

import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

/**
 * Fixes death msg
 */
public class DeathMsgFix implements Listener {
    /**
     * Listens to player death events and displays the death msg correctly
     * @param e {@link PlayerDeathEvent}
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (player.getLastDamageCause() == null) return;
        Entity killer = player.getLastDamageCause().getEntity();
        RegistryEntity entity = RegistryEntity.getRegistryEntity(killer);
        if (entity == null) return;
        e.setDeathMessage(player.getName() + " was killed by " + entity.name);
    }

}
