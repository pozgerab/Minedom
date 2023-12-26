package com.gertoxq.minedom.commands.Stats;

import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class SetManaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        if (!(sender instanceof Player p)) return false;

        RegistryPlayer player = RegistryPlayer.getRegistryPlayer(p);

        try {
            player.manapool = Integer.parseInt(args[0]);
            player.player.sendMessage(ChatColor.AQUA+"Added "+Integer.parseInt(args[0])+" mana!");
            return true;
        } catch (Exception e) {
            player.player.sendMessage("Not an integer!");
        }
        return false;
    }
}
