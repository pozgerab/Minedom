package com.gertoxq.minedom.events.UpdateStats;

import com.gertoxq.minedom.StatSystem.StatSystem;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;

public class UpdateStats implements Listener {

    @EventHandler
    public void onHandChange(PlayerItemHeldEvent e) {
        updateStats(e.getPlayer(), e.getPlayer().getInventory().getItem(e.getNewSlot()));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        updateStats(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());
    }

    @EventHandler
    public void onInventoryChange(InventoryClickEvent e) {
        RegistryPlayer registryPlayer = RegistryPlayer.getRegistryPlayer((Player) e.getWhoClicked());
        Player player = registryPlayer.player;
        if (Arrays.stream(new int[]{100, 101, 102, 102, player.getInventory().getHeldItemSlot()}).boxed().toList().contains(e.getSlot())) {
            updateStats(player, player.getInventory().getItemInMainHand());
        }
    }

    public void updateStats(Player player, ItemStack itemInMainHand) {
        //Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> {

            RegistryPlayer registryPlayer = RegistryPlayer.getRegistryPlayer(player);
            if (registryPlayer == null) return;
            HashMap<Stats, Double> stats = StatSystem.newEmptyPlayerStats();
            if (player.getEquipment() != null) {
                ItemStack[] armor = player.getEquipment().getArmorContents();
                for (ItemStack piece : armor) {
                    if (piece == null || piece.getType() == Material.AIR) continue;
                    RegistryItem item = RegistryItem.getItemByItemStack(piece);
                    if (item == null) continue;
                    StatSystem.addItemStat(stats, item);
                }
            }
            if (itemInMainHand != null && itemInMainHand.getType() == Material.AIR) {
                registryPlayer.equipmentStats = stats;
                registryPlayer.updateStats();
                return;
            }
            RegistryItem registryItem = RegistryItem.getItemByItemStack(itemInMainHand);
            if (registryItem == null) {
                registryPlayer.equipmentStats = stats;
                registryPlayer.updateStats();
                return;
            }
            StatSystem.addItemStat(stats,registryItem);
            registryPlayer.equipmentStats = stats;
            registryPlayer.updateStats();
        //}, 1L);

    }

}
