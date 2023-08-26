package com.gertoxq.minedom.events.healing;

import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.events.Custom.Events.RegenEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OverrideHeal implements Listener {

    @EventHandler
    public void onHeal(RegenEvent e) {
        if (e.getCause() == RegenEvent.RegenCause.NATURAL) e.setAmount(Stats.calcPlayerHealOnce(e.getPlayer()));
        if (e.getCause() == RegenEvent.RegenCause.BY_PLAYER) {

            e.setAmount(e.getHealedBy().stats.get(Stats.BLESSING) / 100 * e.getAmount());
        }
    }
}
