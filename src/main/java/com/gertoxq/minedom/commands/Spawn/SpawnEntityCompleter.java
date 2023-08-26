package com.gertoxq.minedom.commands.Spawn;

import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SpawnEntityCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        if (args.length == 1) {
            return RegistryEntity.registeredEntityClasses.stream().map(entity -> entity.ID).toList();
        } else if (args.length == 2) {
            return Arrays.stream(EntityType.values()).map(Enum::toString).toList();
        }
        return null;
    }
}
