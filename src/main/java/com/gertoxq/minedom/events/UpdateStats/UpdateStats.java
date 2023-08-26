package com.gertoxq.minedom.events.UpdateStats;

import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.events.Custom.Events.InitEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.item.AbilityItem;
import com.gertoxq.minedom.registry.item.FullsetAbilityItem;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.registry.item.StatItem;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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

    @EventHandler
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

    public void updateArmor(RegistryPlayer registryPlayer) {
        HashMap<Stats, Double> stats = Stats.newEmptyPlayerStats();
        ItemStack[] defarmor = registryPlayer.player.getEquipment().getArmorContents();
        Arrays.stream(EquipmentSlot.values()).forEach(equipmentSlot -> {

            if (equipmentSlot == EquipmentSlot.HAND) return;
            registryPlayer.activeEquipmentAbilities.get(equipmentSlot).clear();
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
                registryPlayer.activeEquipmentAbilities.get(abilityItem.item.getType().getEquipmentSlot()).add(ability);
            }
        }
        registryPlayer.armorStats = stats;
        registryPlayer.updateStats();

        Player player = registryPlayer.player;
        ItemStack[] vanillaArmor = player.getInventory().getArmorContents();
        AbilityItem[] armor = Arrays.stream(vanillaArmor).map(AbilityItem::getAbilityItemByItemStack).toList().toArray(new AbilityItem[0]);
        AbilityItem[] realArmor = Arrays.stream(armor).filter(Objects::nonNull).toList().toArray(new AbilityItem[0]);
        boolean canBeFullSet = realArmor.length == 4;
        FullsetAbilityItem[] fullSet = null;
        if (canBeFullSet) {
            fullSet = Arrays.stream(realArmor).map(a -> a instanceof FullsetAbilityItem item1 ? item1 : null).filter(Objects::nonNull).toList().toArray(new FullsetAbilityItem[0]);
            canBeFullSet = fullSet.length == 4;
        }
        if (canBeFullSet) {
            HashMap<Ability, Boolean> canBe = new HashMap<>();
            List<ArrayList<Ability>> abilityList = Arrays.stream(fullSet).map(a -> a.fullSetAbilities).toList();
            for (Ability ability : abilityList.get(0)) {
                if (ability.triggerType != Ability.TriggerType.FULL_ARMOR) continue;
                canBeFullSet = Arrays.stream(fullSet).allMatch(fullsetAbilityItem -> {
                    for (Ability abil : fullsetAbilityItem.fullSetAbilities) {
                        return abil.id.equals(ability.id);
                    }
                    return false;
                });
                canBe.put(ability, canBeFullSet);
            }
            registryPlayer.activeFullsetAbility.clear();
            canBe.forEach((ability, can) -> {
                if (can) {
                    registryPlayer.activeFullsetAbility.add(ability);
                }
            });
        }
    }

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

        registryPlayer.activeEquipmentAbilities.get(EquipmentSlot.HAND).clear();
        if (!(item instanceof AbilityItem registryItem)) return;
        if (registryItem.abilities == null) return;
        for (Ability ability : registryItem.abilities) {
            if (ability == null) continue;
            if (ability.triggerType != Ability.TriggerType.MAINHAND) continue;
            registryPlayer.activeEquipmentAbilities.get(EquipmentSlot.HAND).add(ability);
        }
    }

    public void updateStats(Player player, ItemStack itemInMainHand) {

        RegistryPlayer registryPlayer = RegistryPlayer.getRegistryPlayer(player);
        if (registryPlayer == null) return;
        new InitEvent(registryPlayer).callEvent();
        updateArmor(registryPlayer);
        updateHand(player, itemInMainHand);

    }

}
