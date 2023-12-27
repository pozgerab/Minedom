package com.gertoxq.minedom.events.Custom.Events.Regen.ManaRegen;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.registry.entity.Entities.DamageIndicator;
import com.gertoxq.minedom.util.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public class ExecuteManaRegen implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onManaReg(ManaRegenEvent e) {
        if (e.isCancelled()) return;

        RegistryPlayer player = e.getRegened();
        Location location = player.player.getLocation();

        double befm = player.manapool;
        player.manapool = Math.min(player.manapool + e.getAmount(), player.stats.getMANA());
        double manaAmount = player.manapool - befm;
        if (manaAmount == 0) return;

        Hologram ind = new Hologram(player.player);
        int id = ind.spawn(location.add(new Vector(Math.random()*1.6-0.8, Math.random()*0.4-0.2, Math.random()*1.6-0.8)), ChatColor.AQUA+""+e.getAmount());

        Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> ind.destroy(id), 20L);

    }
}
