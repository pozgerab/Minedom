package com.gertoxq.minedom.commands.Give;

import com.gertoxq.minedom.registry.item.RegistryItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

/**
 * Custom item spawn command completer
 */
public class GiveCustomItemCompleter implements TabCompleter {
    /**
     * @return A list of all the registered custom items
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return RegistryItem.registryItems.values().stream().map(item -> item.id).toList();
    }
}
