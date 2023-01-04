package com.gertoxq.minedom.events.skillListeners;

import com.gertoxq.minedom.events.Events.MagicHitEvent;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatExpGainListener implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onExpGainFromKills(EntityDamageByEntityEvent e) {
        Entity killer = e.getDamager();
        Entity killed = e.getEntity();
        if (!(killer instanceof Player)) return;
        RegistryPlayer registryKiller = RegistryPlayer.getRegistryPlayer((Player) killer);
        if (registryKiller == null) return;
        if (killed instanceof Player) return;
        RegistryEntity killedEntity = RegistryEntity.getRegistryEntity(killed);
        if (killedEntity == null) return;
        if (killedEntity.expType == null) return;
        if((killedEntity.entity.getHealth() - e.getFinalDamage()) <= 0) {
            registryKiller.addExp(killedEntity);
        }

    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onExpGainFromMagic(MagicHitEvent e) {
        if (e.getTarget().expType == null) return;
        if((e.getTarget().entity.getHealth() - e.getDamage()) <= 0) {
            e.getDamager().addExp(e.getTarget());
        }

    }

}
