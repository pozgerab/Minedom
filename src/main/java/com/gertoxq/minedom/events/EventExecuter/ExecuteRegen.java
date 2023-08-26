package com.gertoxq.minedom.events.EventExecuter;

import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.events.Custom.Events.RegenEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ExecuteRegen implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void executeRegen(RegenEvent e) {
        if (e.isCancelled()) return;
        if (e.getPlayer().player.getHealth() + e.getAmount() > e.getPlayer().stats.get(Stats.HEALTH)) {
            e.getPlayer().player.setHealth(e.getPlayer().stats.get(Stats.HEALTH));
        } else {
            e.getPlayer().player.setHealth(e.getPlayer().player.getHealth() + e.getAmount());
        }
    }
}
