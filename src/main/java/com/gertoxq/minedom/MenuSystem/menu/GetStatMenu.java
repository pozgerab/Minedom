package com.gertoxq.minedom.MenuSystem.menu;

import com.gertoxq.minedom.MenuSystem.Menu;
import com.gertoxq.minedom.MenuSystem.PlayerMenuUtility;
import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GetStatMenu extends Menu {

    public GetStatMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Stats";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        e.setCancelled(true);
        e.getWhoClicked().closeInventory();
        Stat stat = Stat.getStatFromItem(e.getCurrentItem().getType());
        if (stat == null) return;
        e.getWhoClicked().sendMessage(ChatColor.AQUA + "Your " + stat.displayName + " is " + ChatColor.GOLD + RegistryPlayer.getRegistryPlayer(playerMenuUtility.getOwner()).stats.get(stat).toString());

    }

    @Override
    public void setMenuItems() {

        for (Stat stat : Stat.values()) {

            ItemStack item = new ItemStack(stat.asItem, 1);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + stat.displayName);
            ArrayList<String> dmgLore = new ArrayList<>();
            dmgLore.add(ChatColor.AQUA + RegistryPlayer.getRegistryPlayer(playerMenuUtility.getOwner()).stats.get(stat).toString());
            itemMeta.setLore(dmgLore);
            item.setItemMeta(itemMeta);

            inventory.setItem(stat.id+9, item);
        }

    }
}
