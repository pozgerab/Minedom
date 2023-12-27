package com.gertoxq.minedom.events.Custom.Events.Regen;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.events.Custom.Events.Regen.RegenEvent;
import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.registry.entity.Entities.DamageIndicator;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.util.Hologram;
import net.kyori.adventure.text.Component;
import net.minecraft.network.chat.IChatBaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.awt.*;

public class ExecuteRegen implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void executeRegen(RegenEvent e) {
        if (e.isCancelled()) return;
        RegistryEntity entity = e.getHealed();
        Location location = entity.entity.getLocation();

        double healAmount = e.getHealed().entity.getHealth() + e.getAmount() - e.getHealed().entity.getHealth();
        if (healAmount == 0) return;
        entity.entity.setHealth(Math.min(entity.entity.getHealth() + e.getAmount(), entity.stats.getHEALTH()));

        if (!(entity instanceof RegistryPlayer player)) return;

        Hologram ind = new Hologram(player.player);
        int id = ind.spawn(location.add(new Vector(Math.random()*1.6-0.8, Math.random()*0.4-0.2, Math.random()*1.6-0.8)), ChatColor.GREEN+""+healAmount);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> ind.destroy(id), 20L);
    }
}
