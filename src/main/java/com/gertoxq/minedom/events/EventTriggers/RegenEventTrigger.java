package com.gertoxq.minedom.events.EventTriggers;

import com.gertoxq.minedom.events.Custom.Events.RegenEvent;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.Bukkit;
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
        e.setCancelled(true);
        EntityRegainHealthEvent.RegainReason reason = e.getRegainReason();
        RegenEvent.RegenCause cause;
        if (reason == EntityRegainHealthEvent.RegainReason.SATIATED) {
            cause = RegenEvent.RegenCause.NATURAL;
        } else if (reason == EntityRegainHealthEvent.RegainReason.MAGIC) {
            cause = RegenEvent.RegenCause.POTION;
        } else {
            cause = RegenEvent.RegenCause.MAGIC;
        }
        RegenEvent event = new RegenEvent(player, e.getAmount(), cause);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }
}
