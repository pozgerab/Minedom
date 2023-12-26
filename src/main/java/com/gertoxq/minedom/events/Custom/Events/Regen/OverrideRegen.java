package com.gertoxq.minedom.events.Custom.Events.Regen;

import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.events.Custom.Events.Regen.ManaRegen.ManaRegenEvent;
import com.gertoxq.minedom.events.Custom.Events.Regen.RegenEvent;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * Listens to custom regen events and sets values based on player stats
 */
public class OverrideRegen implements Listener {
    /**
     * Overrides regen event based on player stats
     * @param e {@link RegenEvent}
     */
    @EventHandler (priority = EventPriority.LOWEST)
    public void onHeal(RegenEvent e) {
        RegistryPlayer player = (RegistryPlayer) e.getHealed();
        if (player == null) return;
        if (e.getCause() == RegenEvent.RegenCause.NATURAL) e.setAmount(Stat.calcPlayerHealOnce(player));
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onManaRegen(ManaRegenEvent e) {
        e.setAmount(Stat.calcPlayerHealManaOnce(e.getRegened()));
    }
}
