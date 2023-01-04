package com.gertoxq.minedom.commands.Give;

import com.gertoxq.minedom.registry.item.RegistryItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCustomItem implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player && args.length == 1) {
            Player player = ((Player) sender);
            RegistryItem item = RegistryItem.getItemById(args[0]);
            if (item == null) {player.sendMessage("Item not found!"); return true;}
            player.getInventory().setItem(player.getInventory().firstEmpty(), item.item);
            return true;
        }

        return false;
    }
}
