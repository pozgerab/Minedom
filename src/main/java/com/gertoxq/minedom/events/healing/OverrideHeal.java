package com.gertoxq.minedom.events.healing;

import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.events.Custom.Events.RegenEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Listens to custom regen events and sets values based on player stats
 */
public class OverrideHeal implements Listener {
    /**
     * Overrides regen event based on player stats
     * @param e {@link RegenEvent}
     */
    @EventHandler
    public void onHeal(RegenEvent e) {
        if (e.getCause() == RegenEvent.RegenCause.NATURAL) e.setAmount(Stats.calcPlayerHealOnce(e.getPlayer()));
        if (e.getCause() == RegenEvent.RegenCause.BY_PLAYER) {

            e.setAmount(e.getHealedBy().stats.get(Stats.BLESSING) / 100 * e.getAmount());
        }
    }
}
