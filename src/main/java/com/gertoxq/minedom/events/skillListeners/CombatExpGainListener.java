package com.gertoxq.minedom.events.skillListeners;

import com.gertoxq.minedom.events.Events.RegistryDeathEvent;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class CombatExpGainListener implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onExpGainFromMagic(RegistryDeathEvent e) {
        if (e.getEntity().expType == null) return;
        if (e.getKiller() instanceof RegistryPlayer) {
            ((RegistryPlayer) e.getKiller()).addExp(e.getEntity());
        }
    }

}
