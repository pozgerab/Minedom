package com.gertoxq.minedom.commands.Give;

import com.gertoxq.minedom.registry.item.RegisterItems;
import com.gertoxq.minedom.registry.item.RegistryItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class GiveCustomItemCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return RegistryItem.registryItems.values().stream().map(item -> item.id).toList();
    }
}
