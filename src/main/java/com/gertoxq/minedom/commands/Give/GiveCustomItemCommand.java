package com.gertoxq.minedom.commands.Give;

import com.gertoxq.minedom.registry.item.RegistryItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Custom item spawn command executor
 */
public class GiveCustomItemCommand implements CommandExecutor {
    /**
     * Gives the sender a custom item by its id
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player && args.length == 1) {
            Player player = ((Player) sender);
            RegistryItem item = RegistryItem.getItemById(args[0]);
            if (item == null) {player.sendMessage(ChatColor.RED + "Item not found!"); return true;}
            player.getInventory().setItem(player.getInventory().firstEmpty(), item.item);
            player.sendMessage(ChatColor.GREEN + "Item found!");
            return true;
        }

        return false;
    }
}
