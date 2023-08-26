package com.gertoxq.minedom.events.EventExecuter;

import com.gertoxq.minedom.events.Custom.Events.MagicHitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHitEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ExecuteMagicHit implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onMagicHit(MagicHitEvent e) {
        new RegistryHitEvent(e.getDamager(), e.getEntity(), e.getDamage(), RegistryHitEvent.DamageSource.MAGIC).callEvent();
    }
}
