package com.gertoxq.minedom.events;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.MeleeHitEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AttackSpeedApply implements Listener {
    @EventHandler
    public void onHit(MeleeHitEvent e) {
        Bukkit.getScheduler().runTaskLater((Minedom.getPlugin()), () -> e.getEntity().entity.setNoDamageTicks(0), (int) Math.ceil(((-(e.getDamager().stats.getATTACK_SPEED() >= 100 ? 100 : e.getDamager().stats.getATTACK_SPEED()))/13)+10));
    }
}
