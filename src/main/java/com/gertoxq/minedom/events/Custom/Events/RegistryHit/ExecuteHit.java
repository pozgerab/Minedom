package com.gertoxq.minedom.events.Custom.Events.RegistryHit;

import com.gertoxq.minedom.events.Custom.Events.RegistryHit.RegistryHitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ExecuteHit implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onHit(RegistryHitEvent e) {
        if (!e.isCancelled()) e.getEntity().entity.damage(e.getDamage());
    }

}
