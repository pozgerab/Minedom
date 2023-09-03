package com.gertoxq.minedom.commands.Stats;

import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetStatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player && args.length == 1) {
            RegistryPlayer registryPlayer = RegistryPlayer.getRegistryPlayer(player);
            Stats stat = Stats.getStatFromName(args[0]);
            player.sendMessage(registryPlayer.stats.get(stat).toString());
            return true;
        }

        return false;
    }
}
