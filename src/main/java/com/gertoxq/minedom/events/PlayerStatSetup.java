package com.gertoxq.minedom.events;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.StatSystem.StatSystem;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerStatSetup implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        RegistryPlayer registryPlayer = new RegistryPlayer(player);
        for (Skill skill : registryPlayer.skillExps.keySet()) {
            if (!player.getPersistentDataContainer().has(new NamespacedKey(Minedom.getPlugin(), skill.name), PersistentDataType.DOUBLE)) return;
            double exp = player.getPersistentDataContainer().get(new NamespacedKey(Minedom.getPlugin(), skill.name), PersistentDataType.DOUBLE);
            registryPlayer.skillExps.put(skill, exp);
            registryPlayer.skillLevels.put(skill, Skill.CalcLvlFromEXP(skill, exp));
            player.getPersistentDataContainer().remove(new NamespacedKey(Minedom.getPlugin(), skill.name));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        player.setAbsorptionAmount(0);
        RegistryPlayer registryPlayer = RegistryPlayer.getRegistryPlayer(player);
        if (registryPlayer == null) return;
        for (Skill skill : registryPlayer.skillExps.keySet()) {
            player.getPersistentDataContainer().set(new NamespacedKey(Minedom.getPlugin(), skill.name), PersistentDataType.DOUBLE, registryPlayer.skillExps.get(skill));
        }
        RegistryPlayer.players.remove(RegistryPlayer.getRegistryPlayer(player));
    }
}
