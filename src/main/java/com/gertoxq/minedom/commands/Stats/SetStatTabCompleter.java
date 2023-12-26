package com.gertoxq.minedom.commands.Stats;

import com.gertoxq.minedom.Stats.Stat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetStatTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        if (args.length == 1) {
            ArrayList<String> list = new ArrayList<>();
            Arrays.stream(Stat.values()).forEach((stat -> list.add(stat.name)));
            return list;
        }
        return null;
    }
}
