package com.gertoxq.minedom;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.gertoxq.minedom.MenuSystem.PlayerMenuUtility;
import com.gertoxq.minedom.commands.Give.GiveCustomItemCommand;
import com.gertoxq.minedom.commands.Give.GiveCustomItemCompleter;
import com.gertoxq.minedom.commands.Spawn.SpawnEntityCommand;
import com.gertoxq.minedom.commands.Spawn.SpawnEntityCompleter;
import com.gertoxq.minedom.commands.Stats.GetStatCommand;
import com.gertoxq.minedom.commands.Stats.GetStatGUICommand;
import com.gertoxq.minedom.events.*;
import com.gertoxq.minedom.events.AbilityListeners.InteractListener;
import com.gertoxq.minedom.events.AbilityListeners.PublicAbilityListener;
import com.gertoxq.minedom.events.Damage.DamageOut;
import com.gertoxq.minedom.events.EventExecuter.*;
import com.gertoxq.minedom.events.EventTriggers.ProjectileHitEventTrigger;
import com.gertoxq.minedom.events.EventTriggers.RegenEventTrigger;
import com.gertoxq.minedom.events.EventTriggers.RegistryDeathEventTrigger;
import com.gertoxq.minedom.events.EventTriggers.ShootBowEventTrigger;
import com.gertoxq.minedom.events.UpdateStats.UpdateStats;
import com.gertoxq.minedom.events.healing.OverrideHeal;
import com.gertoxq.minedom.events.menuListener.MenuListener;
import com.gertoxq.minedom.events.skillListeners.CombatExpGainListener;
import com.gertoxq.minedom.registry.entity.RegisterEntities;
import com.gertoxq.minedom.registry.item.RegisterItems;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Minedom extends JavaPlugin {

    /**
     * Used to assign {@link PlayerMenuUtility} to players. Used in custom menu system.
     */
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    /**
     * The instance of the plugin
     */
    private static Minedom plugin;

    /**
     * Gets the instance of the plugin
     * @return Plugin
     */
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
        getServer().getPluginManager().registerEvents(new IndicateHealth(), this);
        getServer().getPluginManager().registerEvents(new PlayerStatSetup(), this);
        getServer().getPluginManager().registerEvents(new UpdateStats(), this);
        getServer().getPluginManager().registerEvents(new DamageOut(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PublicAbilityListener(), this);
        getServer().getPluginManager().registerEvents(new DeathMsgFix(), this);
        getServer().getPluginManager().registerEvents(new CombatExpGainListener(), this);
        getServer().getPluginManager().registerEvents(new RegistryDeathEventTrigger(), this);
        getServer().getPluginManager().registerEvents(new ExecuteMagicHit(), this);
        getServer().getPluginManager().registerEvents(new ExecuteHit(), this);
        getServer().getPluginManager().registerEvents(new ExecuteMeleeHit(), this);
        getServer().getPluginManager().registerEvents(new ExecuteDeath(), this);
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
        getServer().getPluginManager().registerEvents(new RespawnSetup(), this);
        getServer().getPluginManager().registerEvents(new OverrideHeal(), this);
        getServer().getPluginManager().registerEvents(new SpawnOverride(), this);
        getServer().getPluginManager().registerEvents(new RegenEventTrigger(), this);
        getServer().getPluginManager().registerEvents(new ExecuteRegen(), this);
        getServer().getPluginManager().registerEvents(new ShootBowEventTrigger(), this);
        getServer().getPluginManager().registerEvents(new ProjectileHitEventTrigger(), this);
        getCommand("stats").setExecutor(new GetStatGUICommand());
        getCommand("getstat").setExecutor(new GetStatCommand());
        getCommand("giveitem").setExecutor(new GiveCustomItemCommand());
        getCommand("giveitem").setTabCompleter(new GiveCustomItemCompleter());
        getCommand("spawn").setExecutor(new SpawnEntityCommand());
        getCommand("spawn").setTabCompleter(new SpawnEntityCompleter());

        Bukkit.getOnlinePlayers().forEach(PlayerStatSetup::setUp);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getOnlinePlayers().forEach(PlayerStatSetup::cleanUp);
        Bukkit.getWorlds().forEach(world -> {
            world.getEntities().forEach(entity -> {
                if (!(entity instanceof Player)) entity.remove();
            });
        });
    }

    /**
     * Gets {@link PlayerMenuUtility} by Player
     * @param p Player
     * @return Player's {@link PlayerMenuUtility}
     */
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

    /**
     * Generates a new namespaced key
     * @param key Key name
     * @return Namespaced key
     */
    public static NamespacedKey newKey(String key) {
        return new NamespacedKey(Minedom.getPlugin(), key);
    }

}
