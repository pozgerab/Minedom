package com.gertoxq.minedom.events.Custom.Events.Regen;

import com.gertoxq.minedom.events.Custom.Events.Regen.ManaRegen.ManaRegenEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class RegenBuffer implements Listener {

    /**
     * Overrides regen event based on player stats
     * @param e {@link RegenEvent}
     */
    @EventHandler (priority = EventPriority.LOW)
    public void onHeal(RegenEvent e) {
        switch (e.getCause()) {
            case BY_PLAYER -> e.setAmount((e.getHealedBy().stats.getBLESSING() / 100) * (e.getHealed().stats.getVITALIS() / 100) * e.getAmount());
            default -> e.setAmount((e.getHealed().stats.getVITALIS() / 100) * e.getAmount());
        }
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onManaRegen(ManaRegenEvent e) {
        e.setAmount((e.getRegened().stats.getMANA_REGEN() / 100) * e.getAmount());
    }
}
