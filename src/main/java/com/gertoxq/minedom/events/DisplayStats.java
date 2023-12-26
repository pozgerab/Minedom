package com.gertoxq.minedom.events;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.events.Custom.Events.Init.InitEvent;
import com.gertoxq.minedom.registry.RegistryPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class DisplayStats implements Listener {

    public static final ArrayList<RegistryPlayer> displayed = new ArrayList<>();
    private int prev;
    private int prevDisp;
    @EventHandler
    public void onInit(InitEvent e) {
        if (displayed.contains(e.getPlayer())) return;
        RegistryPlayer player = e.getPlayer();
        displayed.add(player);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Minedom.getPlugin(), () -> player.player.sendActionBar(Component.text(ChatColor.AQUA + "" + (int) player.manapool + "/" + (int) player.stats.getMANA())), 0L, 1L);
    }
}
