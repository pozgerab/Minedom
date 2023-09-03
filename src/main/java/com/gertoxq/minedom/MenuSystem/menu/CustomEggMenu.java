package com.gertoxq.minedom.MenuSystem.menu;

import com.gertoxq.minedom.MenuSystem.Menu;
import com.gertoxq.minedom.MenuSystem.PlayerMenuUtility;
import com.gertoxq.minedom.StatSystem.EntityState;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.item.items.CustomEgg;
import com.gertoxq.minedom.registry.RegistryPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class CustomEggMenu extends Menu {
    public CustomEggMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.LIGHT_PURPLE+"Egg";
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ItemStack item = playerMenuUtility.getOwner().getEquipment().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(CustomEgg.idKey, PersistentDataType.STRING, ((TextComponent) e.getCurrentItem().getItemMeta().displayName()).content());
        item.setItemMeta(meta);
        e.getView().close();
    }

    @Override
    public void setMenuItems() {

        Stats[] stats = new Stats[] {Stats.DAMAGE,Stats.HEALTH,Stats.DEFENSE};

        for (int i = 0; i < stats.length; i++) {

            Stats stat = stats[i];

            ItemStack item = new ItemStack(stat.asItem, 1);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + stat.displayName);
            ArrayList<String> dmgLore = new ArrayList<>();
            dmgLore.add(ChatColor.AQUA + RegistryPlayer.getRegistryPlayer(playerMenuUtility.getOwner()).stats.get(stat).toString());
            itemMeta.setLore(dmgLore);
            item.setItemMeta(itemMeta);

            inventory.setItem(i+3, item);
        }
        int decrease = 0;
        for (int j = 0; j < RegistryEntity.registeredEntityClasses.size(); j++) {
            RegistryEntity entity = RegistryEntity.registeredEntityClasses.get(j);
            if (entity.state == EntityState.INDICATOR) continue;
            ItemStack mob = new ItemStack(entity.asItem);
            ItemMeta meta = mob.getItemMeta();
            meta.displayName(Component.text(entity.ID));
            mob.setItemMeta(meta);

            inventory.setItem(decrease+9, mob);
            decrease++;
        }
    }
}
