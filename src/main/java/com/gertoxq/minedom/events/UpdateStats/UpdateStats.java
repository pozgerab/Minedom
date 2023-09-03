package com.gertoxq.minedom.events.UpdateStats;

import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.events.Custom.Events.InitEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.item.AbilityItem;
import com.gertoxq.minedom.registry.item.FullsetAbilityItem;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.registry.item.StatItem;
import com.gertoxq.minedom.registry.RegistryPlayer;
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

public class UpdateStats implements Listener {

    @EventHandler
    public void onHandChange(PlayerItemHeldEvent e) {
        updateHand(e.getPlayer(), e.getPlayer().getInventory().getItem(e.getNewSlot()));
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        updateStats(e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand());
    }

    @EventHandler
    public void onInventoryChange(InventoryCloseEvent e) {
        RegistryPlayer registryPlayer = RegistryPlayer.getRegistryPlayer((Player) e.getPlayer());
        if (registryPlayer == null) return;
        Player player = registryPlayer.player;
        updateStats(player, player.getInventory().getItemInMainHand());
    }
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
        HashMap<Stats, Double> stats = Stats.newEmptyPlayerStats();
        ItemStack[] defarmor = registryPlayer.player.getEquipment().getArmorContents();
        Arrays.stream(EquipmentSlot.values()).forEach(equipmentSlot -> {
            if (equipmentSlot == EquipmentSlot.HAND) return;
            registryPlayer.clearActiveAbility(equipmentSlot);
        });
        for (ItemStack piece : defarmor) {
            if (piece == null || piece.getType() == Material.AIR) continue;
            RegistryItem item = RegistryItem.getItemByItemStack(piece);
            if (!(item instanceof StatItem statItem)) continue;
            Stats.addItemStat(stats, statItem);
            if (!(statItem instanceof AbilityItem abilityItem)) continue;
            if (abilityItem.abilities == null) continue;
            for (Ability ability : abilityItem.abilities) {
                if (ability == null) continue;
                if (ability.triggerType != Ability.TriggerType.ARMORSLOT) continue;
                registryPlayer.addActiveAbility(abilityItem.item.getType().getEquipmentSlot(), ability);
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
            List<Ability> preActive = registryPlayer.getActiveFullSetAbilities();
            preActive.forEach(registryPlayer::clearFullSetAbility);
            preActive.clear();
            return;
        }
        if (canBeFullSet) {
            ArrayList<Ability> canBe = new ArrayList<>();
            List<ArrayList<Ability>> abilityList = Arrays.stream(fullSet).map(a -> a.fullSetAbilities).toList();
            for (Ability ability : abilityList.get(0)) {
                if (ability.triggerType != Ability.TriggerType.FULL_ARMOR) continue;
                canBeFullSet = Arrays.stream(fullSet).allMatch(fullsetAbilityItem -> fullsetAbilityItem.fullSetAbilities.stream().filter(ability1 -> ability1.id.equals(ability.id)).findAny().orElse(null) != null);
                if (canBeFullSet) canBe.add(ability);
            }
            List<Ability> preActive = registryPlayer.getActiveFullSetAbilities();
            List<Ability> removed = Util.getRemovedElements(preActive, canBe);
            List<Ability> news = Util.getNewElements(preActive, canBe);
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
        HashMap<Stats, Double> stats = Stats.newEmptyPlayerStats();
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
        Stats.addItemStat(stats, item);
        registryPlayer.handStats = stats;
        registryPlayer.updateStats();

        registryPlayer.clearActiveAbility(EquipmentSlot.HAND);
        if (!(item instanceof AbilityItem registryItem)) return;
        if (registryItem.abilities == null) return;
        for (Ability ability : registryItem.abilities) {
            if (ability == null) continue;
            if (ability.triggerType != Ability.TriggerType.MAINHAND) continue;
            registryPlayer.addActiveAbility(EquipmentSlot.HAND, ability);
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
