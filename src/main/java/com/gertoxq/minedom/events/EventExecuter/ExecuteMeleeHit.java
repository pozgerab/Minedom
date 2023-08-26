package com.gertoxq.minedom.events.EventExecuter;

import com.gertoxq.minedom.events.Custom.Events.MeleeHitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHitEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ExecuteMeleeHit implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onMeleeHit(MeleeHitEvent e) {
        new RegistryHitEvent(e.getDamager(), e.getEntity(), e.getDamage(), RegistryHitEvent.DamageSource.MELEE).callEvent();
    }
}
