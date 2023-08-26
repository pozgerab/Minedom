package com.gertoxq.minedom.registry.item.items;

import com.gertoxq.minedom.MenuSystem.menu.CustomEggMenu;
import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.registry.item.ClickableItem;
import com.gertoxq.minedom.registry.item.RegistryItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class CustomEgg extends RegistryItem implements ClickableItem {

    public static final NamespacedKey hpKey = Minedom.newKey("adminegghpKey");
    public static final NamespacedKey defKey = Minedom.newKey("admineggdefKey");
    public static final NamespacedKey dmgKey = Minedom.newKey("admineggdmgKey");
    public static final NamespacedKey idKey = Minedom.newKey("admineggidKey");
    @Override
    public Material setMaterial() {
        return Material.ALLAY_SPAWN_EGG;
    }

    @Override
    public String setName() {
        return ChatColor.RESET + "" + ChatColor.GOLD + "CUSTOM EGG";
    }

    @Override
    public String setID() {
        return "adminex-egg";
    }

    @Override
    public ArrayList<String> setLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+"Right Click to customize");
        return lore;
    }

    @Override
    public ItemMeta setMeta() {
        SpawnEggMeta meta = (SpawnEggMeta) new ItemStack(Material.ALLAY_SPAWN_EGG).getItemMeta();
        meta.setCustomSpawnedType(null);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(hpKey, PersistentDataType.INTEGER, 20);
        container.set(defKey, PersistentDataType.INTEGER, 0);
        container.set(dmgKey, PersistentDataType.INTEGER, 2);
        container.set(idKey, PersistentDataType.STRING, "iron_golem");
        return meta;
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        new CustomEggMenu(Minedom.getPlayerMenuUtility(e.getPlayer())).open();
    }

    @Override
    public ClickableItem.ClickType trigger() {
        return ClickType.RIGHT_CLICK;
    }

    @Override
    public boolean cancelDefault() {
        return true;
    }
}
