package com.gertoxq.minedom.events.EventExecuter;

import com.gertoxq.minedom.events.Events.MagicHitEvent;
import com.gertoxq.minedom.events.Events.RegistryHitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ExecuteMagicHit implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onMagicHit(MagicHitEvent e) {
        RegistryHitEvent event = new RegistryHitEvent(e.getDamager(), e.getTarget(), e.getDamage(), RegistryHitEvent.DamageSource.MAGIC);
    }
}
