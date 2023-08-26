package com.gertoxq.minedom.events.EventExecuter;

import com.gertoxq.minedom.events.Custom.Events.RegistryDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ExecuteDeath implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(RegistryDeathEvent e) {
        if (!e.isCancelled()) e.getEntity().kill(e.getKiller(), e.getSource());
    }

}
