package com.gertoxq.minedom;

import com.gertoxq.minedom.MenuSystem.PlayerMenuUtility;
import com.gertoxq.minedom.StatSystem.StatSystem;
import com.gertoxq.minedom.commands.Give.GiveCustomItem;
import com.gertoxq.minedom.commands.Stats.GetStatCommand;
import com.gertoxq.minedom.commands.Stats.GetStatGUICommand;
import com.gertoxq.minedom.events.*;
import com.gertoxq.minedom.events.AbilityListeners.PublicAbilityListener;
import com.gertoxq.minedom.events.Damage.DamageOut;
import com.gertoxq.minedom.events.UpdateStats.UpdateStats;
import com.gertoxq.minedom.events.menuListener.MenuListener;
import com.gertoxq.minedom.events.skillListeners.CombatExpGainListener;
import com.gertoxq.minedom.registry.entity.RegisterEntities;
import com.gertoxq.minedom.registry.item.RegisterItems;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Minedom extends JavaPlugin {

    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    private static Minedom plugin;
    public static HashMap<LivingEntity,HashMap<StatSystem, Integer>> stats = new HashMap<>();

    public static Minedom getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        new RegisterItems();
        new RegisterEntities();
        getServer().getPluginManager().registerEvents(new CancelTarget(), this);
        getServer().getPluginManager().registerEvents(new RegistryEntitySpawnListener(), this);
        getServer().getPluginManager().registerEvents(new IndicateHealth(), this);
        getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerStatSetup(), this);
        getServer().getPluginManager().registerEvents(new UpdateStats(), this);
        getServer().getPluginManager().registerEvents(new DamageOut(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PublicAbilityListener(), this);
        getServer().getPluginManager().registerEvents(new DeathMsgFix(), this);
        getServer().getPluginManager().registerEvents(new CombatExpGainListener(), this);
        getCommand("stats").setExecutor(new GetStatGUICommand());
        getCommand("getstat").setExecutor(new GetStatCommand());
        getCommand("giveitem").setExecutor(new GiveCustomItem());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;

        if (playerMenuUtilityMap.containsKey(p)) {
            return playerMenuUtilityMap.get(p);
        } else {

            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);

            return playerMenuUtility;
        }
    }

}
