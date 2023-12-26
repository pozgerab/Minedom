package com.gertoxq.minedom.events;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.events.Custom.Events.Regen.ManaRegen.ManaRegenTrigger;
import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Player data setup class
 */
public class PlayerStatSetup implements Listener {

    /**
     * Listens to join events and sets up player data
     * @param e {@link PlayerJoinEvent}
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        setUp(player);
    }

    /**
     * Sets up player data
     * @param player Player to be set up
     */
    public static void setUp(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, -1, 25, false, false, false));
        RegistryPlayer registryPlayer = new RegistryPlayer(player);
        for (Skill skill : registryPlayer.skillExps.keySet()) {
            if (!player.getPersistentDataContainer().has(new NamespacedKey(Minedom.getPlugin(), skill.name), PersistentDataType.DOUBLE)) return;
            double exp = player.getPersistentDataContainer().get(Minedom.newKey(skill.name), PersistentDataType.DOUBLE);
            registryPlayer.skillExps.put(skill, exp);
            registryPlayer.skillLevels.put(skill, Skill.CalcLvlFromEXP(skill, exp));
            player.getPersistentDataContainer().remove(new NamespacedKey(Minedom.getPlugin(), skill.name));
        }
        new ManaRegenTrigger(registryPlayer);
    }

    /**
     * Listens for quit events and cleans up player data
     * @param e {@link PlayerQuitEvent}
     */
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        cleanUp(player);
    }

    /**
     * Cleans up player data
     * @param player Player to be cleaned
     */
    public static void cleanUp(Player player) {
        player.setAbsorptionAmount(0);
        RegistryPlayer registryPlayer = RegistryPlayer.getRegistryPlayer(player);
        if (registryPlayer == null) return;
        for (Skill skill : registryPlayer.skillExps.keySet()) {
            player.getPersistentDataContainer().set(new NamespacedKey(Minedom.getPlugin(), skill.name), PersistentDataType.DOUBLE, registryPlayer.skillExps.get(skill));
        }
        RegistryPlayer.players.remove(RegistryPlayer.getRegistryPlayer(player));
    }
}
