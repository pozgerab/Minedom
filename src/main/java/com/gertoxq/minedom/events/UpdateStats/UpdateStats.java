package com.gertoxq.minedom.events.UpdateStats;

import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.events.Custom.Events.Init.InitEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.ItemAbility;
import com.gertoxq.minedom.registry.item.*;
import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.util.StatContainter;
import com.gertoxq.minedom.util.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Used to update the stats of players
 */
public class UpdateStats implements Listener {

    /**
     * Listens to hand change and updates hand
     * @param e {@link PlayerItemHeldEvent}
     */
    @EventHandler
    public void onHandChange(PlayerItemHeldEvent e) {
        updateHand(e.getPlayer(), e.getPlayer().getInventory().getItem(e.getNewSlot()));
    }

    /**
     * Listens to player join events and updates all stats
     * @param e {@link PlayerJoinEvent}
     */
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        updateStats(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());
    }

    /**
     * Listens to inventory close events and updates all stats
     * @param e {@link InventoryCloseEvent}
     */
    @EventHandler
    public void onInventoryChange(InventoryCloseEvent e) {
        RegistryPlayer registryPlayer = RegistryPlayer.getRegistryPlayer((Player) e.getPlayer());
        if (registryPlayer == null) return;
        Player player = registryPlayer.player;
        updateStats(player, player.getInventory().getItemInMainHand());
    }

    /**
     * Listens to player interact events and updates hand or +armor
     * @param e {@link PlayerInteractEvent}
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        RegistryPlayer player = RegistryPlayer.getRegistryPlayer(e.getPlayer());
        if (player == null) return;
        ItemStack item = e.getItem();
        if (item == null) return;
        if (item.getType().getEquipmentSlot().isArmor()) updateArmor(player);
    }

    /**
     * Updates armor stats and abilities
     * @param registryPlayer Player to check
     */
    public void updateArmor(RegistryPlayer registryPlayer) {
        StatContainter stats = Stat.newEmptyPlayerStats();
        ItemStack[] defarmor = registryPlayer.player.getEquipment().getArmorContents();
        Arrays.stream(EquipmentSlot.values()).forEach(equipmentSlot -> {
            if (equipmentSlot == EquipmentSlot.HAND) return;
            registryPlayer.clearActiveAbility(equipmentSlot);
        });
        for (ItemStack piece : defarmor) {
            if (piece == null || piece.getType() == Material.AIR) continue;
            RegistryItem item = RegistryItem.getItemByItemStack(piece);
            if (!(item instanceof StatItem statItem)) continue;
            Stat.addItemStat(stats, statItem);
            if (!(statItem instanceof AbilityItem abilityItem)) continue;
            if (abilityItem.abilities == null) continue;
            for (Ability ability : abilityItem.abilities) {
                if (ability == null) continue;
                if (!(ability instanceof ItemAbility)) return;
                if (ability.getTriggerType() != ItemAbility.TriggerType.ARMORSLOT) continue;
                registryPlayer.addActiveAbility(abilityItem.item.getType().getEquipmentSlot(), (ItemAbility) ability);
            }
        }
        registryPlayer.armorStats = stats;
        registryPlayer.updateStats();

        Player player = registryPlayer.player;
        ItemStack[] vanillaArmor = player.getEquipment().getArmorContents();
        AbilityItem[] armor = Arrays.stream(vanillaArmor).map(AbilityItem::getAbilityItemByItemStack).toList().toArray(new AbilityItem[0]);
        AbilityItem[] realArmor = Arrays.stream(armor).filter(Objects::nonNull).toList().toArray(new AbilityItem[0]);
        boolean canBeFullSet = realArmor.length == 4;
        FullsetAbilityItem[] fullSet;
        //registryPlayer.removeFullSetAbility();
        if (canBeFullSet) {
            fullSet = Arrays.stream(realArmor).map(a -> a instanceof FullsetAbilityItem item1 ? item1 : null).filter(Objects::nonNull).toList().toArray(new FullsetAbilityItem[0]);
            canBeFullSet = fullSet.length == 4;
        } else {
            List<ItemAbility> preActive = registryPlayer.getActiveFullSetAbilities();
            preActive.forEach(registryPlayer::clearFullSetAbility);
            preActive.clear();
            return;
        }
        if (canBeFullSet) {
            ArrayList<ItemAbility> canBe = new ArrayList<>();
            List<ArrayList<ItemAbility>> abilityList = Arrays.stream(fullSet).map(a -> a.fullSetAbilities).toList();
            for (ItemAbility ability : abilityList.get(0)) {
                if (ability.getTriggerType() != ItemAbility.TriggerType.FULL_ARMOR) continue;
                canBeFullSet = Arrays.stream(fullSet).allMatch(fullsetAbilityItem -> fullsetAbilityItem.fullSetAbilities.stream().filter(ability1 -> ability1.getId().equals(ability.getId())).findAny().orElse(null) != null);
                if (canBeFullSet) canBe.add(ability);
            }
            List<ItemAbility> preActive = registryPlayer.getActiveFullSetAbilities();
            List<ItemAbility> removed = Util.getRemovedElements(preActive, canBe);
            List<ItemAbility> news = Util.getNewElements(preActive, canBe);
            news.forEach(registryPlayer::addFullSetAbility);
            removed.forEach(registryPlayer::removeFullSetAbility);
        }
    }

    /**
     * Updates item's stats
     * @param player Player to check
     * @param itemInMainHand Item to check
     */
    public void updateHand(Player player, ItemStack itemInMainHand) {
        RegistryPlayer registryPlayer = RegistryPlayer.getRegistryPlayer(player);
        if (registryPlayer == null) return;
        StatContainter stats = Stat.newEmptyPlayerStats();
        if (itemInMainHand != null && itemInMainHand.getType() == Material.AIR) {
            registryPlayer.handStats = stats;
            registryPlayer.updateStats();
            return;
        }
        StatItem item = StatItem.getStatItemByItemStack(itemInMainHand);
        if (item == null) {
            registryPlayer.handStats = stats;
            registryPlayer.updateStats();
            return;
        }
        Stat.addItemStat(stats, item);
        registryPlayer.handStats = stats;
        registryPlayer.updateStats();

        registryPlayer.clearActiveAbility(EquipmentSlot.HAND);
        if (!(item instanceof AbilityItem registryItem)) return;
        if (registryItem.abilities == null) return;
        for (Ability ability : registryItem.abilities) {
            if (ability == null) continue;
            if (!(ability instanceof ItemAbility)) return;
            if (ability.getTriggerType() != ItemAbility.TriggerType.MAINHAND) continue;
            registryPlayer.addActiveAbility(EquipmentSlot.HAND, (ItemAbility) ability);
        }
    }

    /**
     * Updates the player's stats and abilities
     * @param player Player to update
     * @param itemInMainHand Item to update
     * @Triggers {@link InitEvent}
     */
    public void updateStats(Player player, ItemStack itemInMainHand) {

        RegistryPlayer registryPlayer = RegistryPlayer.getRegistryPlayer(player);
        if (registryPlayer == null) return;
        updateArmor(registryPlayer);
        updateHand(player, itemInMainHand);
        new InitEvent(registryPlayer).callEvent();
    }

}
