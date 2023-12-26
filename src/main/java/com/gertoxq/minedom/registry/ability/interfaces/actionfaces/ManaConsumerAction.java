package com.gertoxq.minedom.registry.ability.interfaces.actionfaces;

import com.gertoxq.minedom.registry.RegistryPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;

public interface ManaConsumerAction {

    /**
     * @return The amount of mana the ability consumes
     */
    double getManaCost();

    default boolean consume(RegistryPlayer player) {
        if (getManaCost() > player.manapool) {
            player.player.sendMessage(Component.text(ChatColor.RED+"Not enough mana"));
            return false;
        };
        player.manapool = player.manapool - getManaCost();
        return true;
    }
}
