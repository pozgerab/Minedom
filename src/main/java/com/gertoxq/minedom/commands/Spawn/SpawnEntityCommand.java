package com.gertoxq.minedom.commands.Spawn;

import com.gertoxq.minedom.registry.entity.Entities.VanillaEntity;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Spawn custom entity command executor
 */
public class SpawnEntityCommand implements CommandExecutor {
    /**
     * Spawns a new custom entity by id, or id arg no.1 is #def (vanilla entity), spawns a vanilla entity by id on arg no.2
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NonNull @NotNull String[] args) {
        if (sender instanceof Player player && args.length == 1) {

            RegistryEntity entity = RegistryEntity.byId(args[0]);
            try {
                entity.getClass().getDeclaredConstructor().newInstance().spawn(player.getLocation());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        } else if (sender instanceof Player player && args.length == 2) {

            RegistryEntity entity = RegistryEntity.byId(args[0]);
            if (!Objects.equals(entity.ID, new VanillaEntity().ID)) return false;
            EntityType type;
            try {
                type = EntityType.valueOf(args[1]);
            } catch (IllegalArgumentException e) {
                return false;
            }
            new VanillaEntity(type).spawn(player.getLocation());
        }

        return false;
    }
}
