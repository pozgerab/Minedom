package com.gertoxq.minedom.events.menuListener;

import com.gertoxq.minedom.MenuSystem.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null) return;
        InventoryHolder holder = e.getClickedInventory().getHolder();

        if (holder instanceof Menu menu) {

            e.setCancelled(true);

            if (e.getCurrentItem() == null) {
                return;
            }

            menu.handleMenu(e);
        }

    }

}
