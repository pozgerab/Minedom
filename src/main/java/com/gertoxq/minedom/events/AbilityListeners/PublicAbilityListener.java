package com.gertoxq.minedom.events.AbilityListeners;

import com.gertoxq.minedom.events.Events.MagicHitEvent;
import com.gertoxq.minedom.events.Events.RegistryDeathEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PublicAbilityListener implements Listener {

    @EventHandler
    public void onMeleeHit(EntityDamageByEntityEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if (e.getDamager() instanceof Player player) {
                RegistryPlayer registryPlayer = RegistryPlayer.getRegistryPlayer(player);
                if (registryPlayer == null) return;
                searchAbilityUsage(registryPlayer, e);
            }
        }
    }

    @EventHandler
    public void onMagicHit(MagicHitEvent e) {
        if (e.isLock()) return;
        if (e.getDamager() instanceof RegistryPlayer) {
            searchAbilityUsage((RegistryPlayer) e.getDamager(), e);
        }
    }

    @EventHandler
    public void onDeath(RegistryDeathEvent e) {
        if (e.getKiller() instanceof RegistryPlayer) {
            searchAbilityUsage((RegistryPlayer) e.getKiller(), e);
        }
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            RegistryPlayer registryPlayer = RegistryPlayer.getRegistryPlayer((Player) e.getEntity());
            if (registryPlayer == null) return;
            searchAbilityUsage(registryPlayer, e);
        }

    }

    @EventHandler
    public void projectileHit(ProjectileHitEvent e) {
        if (e.getEntity().getShooter() instanceof Player player) {
            RegistryPlayer registryPlayer = RegistryPlayer.getRegistryPlayer((Player) e.getEntity().getShooter());
            if (registryPlayer == null) return;
            searchAbilityUsage(registryPlayer, e);
        }

    }

    public void searchAbilityUsage(RegistryPlayer registryPlayer, Event e) {
        if (registryPlayer == null) return;
        Player player = registryPlayer.player;
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemStack[] armor = player.getInventory().getArmorContents();
        for (ItemStack piece : armor) {
            if (piece == null) continue;
            RegistryItem registryPiece = RegistryItem.getItemByItemStack(piece);
            if (registryPiece == null) continue;
            for (Ability ability : registryPiece.abilities) {
                if (ability == null) continue;
                if (ability.event.contains(e.getClass())) {
                    ability.handleEvent(e, registryPlayer);
                }
            }
        }
        RegistryItem registryItem = RegistryItem.getItemByItemStack(item);
        if (registryItem == null) return;
        for (Ability ability : registryItem.abilities) {
            if (ability == null) continue;
            if (ability.event.contains(e.getClass())) {
                ability.handleEvent(e, registryPlayer);
            }
        }
    }
}
