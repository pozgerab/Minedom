package com.gertoxq.minedom.commands.Spawn;

import com.gertoxq.minedom.registry.entity.Entities.VanillaEntity;
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

/**
 * Spawn custom entity command completer
 */
public class SpawnEntityCompleter implements TabCompleter {
    /**
     * @return A list of all registered custom entities on arg no.1 and if arg no.1 is #def (vanilla entity)
     * a list of all vanilla entities.
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        if (args.length == 1) {
            return RegistryEntity.registeredEntityClasses.stream().map(entity -> entity.ID).toList();
        } else if (args.length == 2) {
            RegistryEntity entity = RegistryEntity.byId(args[0]);
            if (!entity.ID.equals( new VanillaEntity().ID)) return null;
            return Arrays.stream(EntityType.values()).map(Enum::toString).toList();
        }
        return null;
    }
}
