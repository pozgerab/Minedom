package com.gertoxq.minedom.events.skillListeners;

import com.gertoxq.minedom.events.Custom.Events.RegistryDeathEvent;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * Gives players exp upon killing entities
 */
public class CombatExpGainListener implements Listener {
    /**
     * Listens to death events and gives the killer the exp
     * @param e {@link RegistryDeathEvent}
     */
    @EventHandler (priority = EventPriority.HIGH)
    public void onExpGainFromMagic(RegistryDeathEvent e) {
        if (e.isCancelled()) return;
        if (e.getEntity().expType == null) return;
        if (e.getKiller() instanceof RegistryPlayer) {
            ((RegistryPlayer) e.getKiller()).addExp(e.getEntity());
        }
    }

}
