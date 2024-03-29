package com.gertoxq.minedom.events.AbilityListeners;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.Init.InitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.MagicHitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.MeleeHitEvent;
import com.gertoxq.minedom.events.Custom.Events.ProjectileHit.ProjectileHitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryDeath.RegistryDeathEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.RegistryHitEvent;
import com.gertoxq.minedom.events.Custom.Events.ShootBow.ShootBowEvent;
import com.gertoxq.minedom.events.Custom.REvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.ItemAbility;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.item.AbilityItem;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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
    public <T extends REvent & AEvent> void onAny(T e) {
        if (!(e instanceof AEvent)) return;
        if (e.getTriggerer() instanceof RegistryPlayer player) searchAbilityUsage(player, e);
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

    /**
     * Searches and executes abilities based on the passed event.
     * @param registryPlayer Player to check
     * @param e Event
     */
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
            if (!(ability instanceof ItemAbility)) continue;
            if (!Arrays.stream(ability.getClass().getInterfaces()).toList().contains(e.getTriggerFace())) continue;
            if (ability.getTriggerType() != ItemAbility.TriggerType.MAINHAND) continue;
            ability.handleEvent(e, registryPlayer);

        }
    }

    /**
     * Executes abilities in a specific slot (only armor slots)
     * @param e Event
     * @param player Checked player
     * @param slot Equipment slot
     */
    private void cacheAbility(AEvent e, RegistryPlayer player, EquipmentSlot slot) {
        List<ItemAbility> list = player.getActiveEquipmentAbilities().get(slot);
        list.forEach(ability -> {
            if (ability != null && Arrays.stream(ability.getClass().getInterfaces()).toList().contains(e.getTriggerFace()) && ability.getTriggerType() == ItemAbility.TriggerType.ARMORSLOT) {
                ability.handleEvent(e, player);
            }
        });
    }

    /**
     * Executes a player's full set ability
     * @param e Event
     * @param player Checked player
     */
    private void cacheFullSetAbility(AEvent e, RegistryPlayer player) {
        player.getActiveFullSetAbilities().forEach(ability -> {
            if (ability != null && Arrays.stream(ability.getClass().getInterfaces()).toList().contains(e.getTriggerFace()) && ability.getTriggerType() == ItemAbility.TriggerType.FULL_ARMOR) {
                ability.handleEvent(e, player);
            }
        });
    }
}
