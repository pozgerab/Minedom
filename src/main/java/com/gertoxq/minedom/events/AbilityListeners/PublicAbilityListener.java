package com.gertoxq.minedom.events.AbilityListeners;

import com.gertoxq.minedom.events.Custom.Events.*;
import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.ProjectileHitEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.item.AbilityItem;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Listens to events and executes abilities
 */
public class PublicAbilityListener implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onInit(InitEvent e) {
        searchAbilityUsage(e.getPlayer(), e);
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onMagicHit(MagicHitEvent e) {
        if (e.isLock()) return;
        if (e.getDamager() instanceof RegistryPlayer player) {
            searchAbilityUsage(player, e);
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onMeleeHit(MeleeHitEvent e) {
        if (e.getDamager() instanceof RegistryPlayer player) {
            searchAbilityUsage(player, e);
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onDeath(RegistryDeathEvent e) {
        if (e.getKiller() instanceof RegistryPlayer) {
            searchAbilityUsage((RegistryPlayer) e.getKiller(), e);
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onBowShoot(ShootBowEvent e) {
        if (e.getShooter() instanceof RegistryPlayer player) {
            searchAbilityUsage(player, e);
        }

    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void projectileHit(ProjectileHitEvent e) {
        if (e.getEntity().getShooter() instanceof Player player) {
            RegistryPlayer registryPlayer = RegistryPlayer.getRegistryPlayer(player);
            if (registryPlayer == null) return;
            searchAbilityUsage(registryPlayer, e);
        }

    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void hit(RegistryHitEvent e) {
        if (e.getDamager() instanceof RegistryPlayer player) {
            searchAbilityUsage(player, e);
        }

    }


    public void searchAbilityUsage(RegistryPlayer registryPlayer, AEvent e) {
        Player player = registryPlayer.player;
        ItemStack[] vanillaArmor = player.getInventory().getArmorContents();
        AbilityItem[] armor = Arrays.stream(vanillaArmor).map(AbilityItem::getAbilityItemByItemStack).toList().toArray(new AbilityItem[0]);
        AbilityItem[] realArmor = Arrays.stream(armor).filter(Objects::nonNull).toList().toArray(new AbilityItem[0]);
        ItemStack item = registryPlayer.player.getInventory().getItemInMainHand();
        cacheFullSetAbility(e, registryPlayer);
        for (AbilityItem piece : realArmor) {
            cacheAbility(e, registryPlayer, piece.item.getType().getEquipmentSlot());
        }
        AbilityItem registryItem = AbilityItem.getAbilityItemByItemStack(item);
        if (registryItem == null) return;
        if (registryItem.abilities == null) return;
        for (Ability ability : registryItem.abilities) {
            if (ability == null) continue;
            if (!Arrays.stream(ability.getClass().getInterfaces()).toList().contains(e.getTriggerFace())) continue;
            if (ability.triggerType != Ability.TriggerType.MAINHAND) continue;
            ability.handleEvent(e, registryPlayer);

        }
    }

    private void cacheAbility(AEvent e, RegistryPlayer player, EquipmentSlot slot) {
        List<Ability> list = player.getActiveEquipmentAbilities().get(slot);
        list.forEach(ability -> {
            if (ability != null && Arrays.stream(ability.getClass().getInterfaces()).toList().contains(e.getTriggerFace()) && ability.triggerType == Ability.TriggerType.ARMORSLOT) {
                ability.handleEvent(e, player);
            }
        });
    }
    private void cacheFullSetAbility(AEvent e, RegistryPlayer player) {
        player.getActiveFullSetAbility().forEach(ability -> {
            if (ability != null && Arrays.stream(ability.getClass().getInterfaces()).toList().contains(e.getTriggerFace()) && ability.triggerType == Ability.TriggerType.FULL_ARMOR) {
                ability.handleEvent(e, player);
            }
        });
    }
}
