package com.gertoxq.minedom.events.AbilityListeners;

import com.gertoxq.minedom.registry.item.ClickableItem;
import com.gertoxq.minedom.registry.item.RegistryItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        RegistryItem item = RegistryItem.getItemByItemStack(e.getItem());
        if (item == null) return;
        if (!(item instanceof ClickableItem)) return;

        ClickableItem.emit(((ClickableItem) item), e);
    }
}
