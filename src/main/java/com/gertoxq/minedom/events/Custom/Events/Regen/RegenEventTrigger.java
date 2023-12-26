package com.gertoxq.minedom.events.Custom.Events.Regen;

import com.gertoxq.minedom.events.Custom.Events.Regen.RegenEvent;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class RegenEventTrigger implements Listener {

    @EventHandler
    public void onHeal(EntityRegainHealthEvent e) {
        if (!(e.getEntity() instanceof Player vanillaPlayer)) return;
        RegistryPlayer player = RegistryPlayer.getRegistryPlayer(vanillaPlayer);
        if (player == null) return;
        EntityRegainHealthEvent.RegainReason reason = e.getRegainReason();
        RegenEvent.RegenCause cause = (switch (reason) {
            case SATIATED -> RegenEvent.RegenCause.NATURAL;
            case MAGIC -> RegenEvent.RegenCause.POTION;
            default -> RegenEvent.RegenCause.MAGIC;
        });
        new RegenEvent(player, e.getAmount(), cause).callEvent();
        e.setCancelled(true);
    }
}
