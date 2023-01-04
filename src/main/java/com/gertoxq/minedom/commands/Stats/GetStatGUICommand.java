package com.gertoxq.minedom.commands.Stats;

import com.gertoxq.minedom.MenuSystem.menu.GetStatMenu;
import com.gertoxq.minedom.Minedom;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetStatGUICommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            new GetStatMenu(Minedom.getPlayerMenuUtility(player)).open();
        }
        
        return false;
    }
}
