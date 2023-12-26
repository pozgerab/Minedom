package com.gertoxq.minedom.commands.Stats;

import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class SetStatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        if (!(sender instanceof Player p)) return false;

        RegistryPlayer player = RegistryPlayer.getRegistryPlayer(p);

        Stat stat = Stat.getStatFromName(args[0]);
        if (stat == null) return false;
        try {
            int amount = Integer.parseInt(args[1]);
            player.profileStats.increase(stat, amount);
            player.player.sendMessage(ChatColor.GRAY+"You increased your "+stat.displayName+" by "+amount);
            return true;
        } catch (Exception ignored) {
            player.player.sendMessage("Not an int");
            return false;
        }
    }
}
