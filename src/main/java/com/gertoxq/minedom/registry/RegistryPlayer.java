package com.gertoxq.minedom.registry;

import com.gertoxq.minedom.Stats.EntityState;
import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.registry.ability.ItemAbility;
import com.gertoxq.minedom.registry.ability.action.Statable;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.skill.Skill;
import com.gertoxq.minedom.util.StatContainter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Custom player class
 */
public class RegistryPlayer extends RegistryEntity {
    /**
     * List of players
     */
    public static ArrayList<RegistryPlayer> players = new ArrayList<>();

    /**
     * Bukkit player
     */
    public Player player;
    /**
     * Stats gained from {@link EquipmentSlot#HAND} slot
     */
    public StatContainter handStats = new StatContainter(this);
    /**
     * Stats gained from armor slots
     */
    public StatContainter armorStats = new StatContainter(this);
    /**
     * Stats gained from other sources
     */
    public final StatContainter profileStats = getStats();
    /**
     * Stats gained by abilities
     */
    public final StatContainter abilityStats = new StatContainter(this);
    /**
     * Player UUID
     */
    public UUID uuid;
    /**
     * Whether the player joined before
     */
    public Boolean joined;
    /**
     * Player skill level container
     */
    public HashMap<Skill, Double> skillLevels = Skill.newEmptySkills();
    /**
     * Player skill exp container
     */
    public HashMap<Skill, Double> skillExps = Skill.newEmptySkills();
    /**
     * Player's active abilities from their armor
     */
    private final HashMap<EquipmentSlot, List<ItemAbility>> activeEquipmentAbilities = new HashMap<>();
    /**
     * Player's active full set abilities
     */
    private final List<ItemAbility> activeFullSetAbility = new ArrayList<>();

    /**
     * The mana the player currently has
     */
    public double manapool;

    public boolean loaded = false;

    /**
     * Init of Player
     * @param player Bukkit Player
     */
    public RegistryPlayer(Player player) {
        super("player", player);
        this.stats = Stat.sumStats(this);
        updateManaPool();
        this.uuid = player.getUniqueId();
        this.joined = true;
        this.player = player;
        this.player.setHealthScale(40.0);
        this.state = EntityState.PLAYER;
        Arrays.stream(EquipmentSlot.values()).forEach(equipmentSlot -> {
            activeEquipmentAbilities.put(equipmentSlot, new ArrayList<>());
        });
        players.add(this);
        loaded = true;
    }

    /**
     * Gets the player from Bukkit player
     * @param player Bukkit Player
     * @return Registry Player
     */
    public static RegistryPlayer getRegistryPlayer(Player player) {

        RegistryPlayer registryPlayer = players.stream().filter(e -> e.uuid.compareTo(player.getUniqueId()) == 0).findAny().orElse(null);
        if (registryPlayer == null) return null;
        registryPlayer.stats = Stat.sumStats(registryPlayer);
        double speed = registryPlayer.stats.getAGILITY();
        double health = registryPlayer.stats.getHEALTH();
        registryPlayer.player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        registryPlayer.player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed / 1000);
        return registryPlayer;
    }

    public void updateManaPool() {
        this.manapool = this.stats.getMANA();
    }

    /**
     * Get active equipment abilities
     * @return Ability map
     */
    public HashMap<EquipmentSlot, List<ItemAbility>> getActiveEquipmentAbilities() {
        return activeEquipmentAbilities;
    }

    /**
     * Gets active full set abilities
     * @return Ability List
     */
    public List<ItemAbility> getActiveFullSetAbilities() {
        return activeFullSetAbility;
    }

    /**
     * Adds active equipment ability
     * @param slot Responsible slot for ability
     * @param ability Ability
     */
    public void addActiveAbility(EquipmentSlot slot, ItemAbility ability) {
        activeEquipmentAbilities.get(slot).add(ability);
    }

    /**
     * Clears active equipment abilities for one slot
     * @param slot Responsible slot
     */
    public void clearActiveAbility(EquipmentSlot slot) {
        List<ItemAbility> abilities = activeEquipmentAbilities.get(slot);
        abilities.forEach(ability -> {
            ability.getActions().forEach(action -> {
                if (action instanceof Statable stateAction) {
                    stateAction.cleanUp(this);
                }
            });
        });
        activeEquipmentAbilities.get(slot).clear();

    }

    /**
     * Adds full set ability
     * @param ability Ability
     */
    public void addFullSetAbility(ItemAbility ability) {
        activeFullSetAbility.add(ability);
    }

    /**
     * Clears active full set ability. Runs the {@link Statable#cleanUp(RegistryPlayer player)} on the player.
     * If throws {@link ConcurrentModificationException}, use {@link #clearFullSetAbility(ItemAbility)} instead and separately remove the abilities from list.
     * @param ability Ability to be removed.
     * @throws ConcurrentModificationException if iterates through {@link #activeFullSetAbility}.
     */
    public void removeFullSetAbility(ItemAbility ability) {
        ability.getActions().forEach(action -> {
            if (action instanceof Statable stateAction) {
                stateAction.cleanUp(this);
            }
        });
        activeFullSetAbility.remove(ability);
    }

    /**
     * Used to avoid {@link ConcurrentModificationException}.
     * Similar to {@link #removeFullSetAbility(ItemAbility ability)}, but doesn't remove the ability from the ability list, so that needs to be cleared separately.
     * @param ability Ability to be removed.
     */
    public void clearFullSetAbility(ItemAbility ability) {
        ability.getActions().forEach(action -> {
            if (action instanceof Statable stateAction) {
                stateAction.cleanUp(this);
            }
        });
    }

    /**
     * Refreshes the player's stats
     */
    public void updateStats() {
        double prevMana = stats.getMANA();
        double prevPool = manapool;
        double manaScale = prevPool/prevMana;
        stats = Stat.sumStats(this);
        double prevMax = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double prevHp = player.getHealth();
        double scale =  (prevHp / prevMax) > 1 ? 1 : prevHp / prevMax;
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(stats.getHEALTH());
        if (prevMax != stats.get(Stat.HEALTH)) {
            player.setHealth(stats.get(Stat.HEALTH) * scale);
        }
        if (prevMana != stats.getMANA()) {
            updateManaPool();
            manapool = stats.getMANA()*manaScale;
        }
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(stats.getAGILITY() / 1000);
        player.setHealthScale(40);
    }

    /**
     * Adds and displays exp from a killed entity
     * @param killedEntity Registry entity
     */
    public void addExp(RegistryEntity killedEntity) {
        if (killedEntity.expType == null) return;
        Skill.addSkillExp(killedEntity.expType, this, killedEntity.expDrop);
        this.player.showTitle(Title.title(Component.empty(), Component.text(ChatColor.GREEN + "+" + killedEntity.expDrop + " " + killedEntity.expType.displayName + " exp")));
        double newExp = this.skillExps.get(killedEntity.expType) + killedEntity.expDrop;
        this.skillExps.put(killedEntity.expType, newExp);
        double newLevel = Skill.CalcLvlFromEXP(killedEntity.expType, newExp);
        if (newLevel > this.skillLevels.get(killedEntity.expType)) {
            this.skillLevels.put(killedEntity.expType, newLevel);
            this.player.sendMessage(ChatColor.AQUA + "SKILL LEVEL UP");
            this.player.sendMessage(ChatColor.GOLD + killedEntity.expType.displayName + " " + (int) newLevel);
        }
    }

    /**
     * Adds skill exp
     * @param skill Improved skill
     * @param amount Exp amount
     */
    public void addExp(Skill skill, double amount) {
        Skill.addSkillExp(skill, this, amount);
        this.player.showTitle(Title.title(Component.empty(), Component.text(ChatColor.GREEN + "+" + amount + " " + skill.displayName + " exp")));
        double newExp = this.skillExps.get(skill) + amount;
        this.skillExps.put(skill, newExp);
        double newLevel = Skill.CalcLvlFromEXP(skill, newExp);
        if (newLevel > this.skillLevels.get(skill)) {
            this.skillLevels.put(skill, newLevel);
            this.player.sendMessage(ChatColor.AQUA + "SKILL LEVEL UP");
            this.player.sendMessage(ChatColor.GOLD + skill.displayName + " " + (int) newLevel);
        }
    }


    @NotNull
    @Override
    public Material asItem() {
        return Material.PLAYER_HEAD;
    }

    @NotNull
    @Override
    public EntityType getType() {
        return EntityType.PLAYER;
    }

    @Override
    @NotNull
    public StatContainter getStats() {
        StatContainter containter = new StatContainter(this);
        containter.setHEALTH(20);
        containter.setDAMAGE(5);
        containter.setMANA(100);
        containter.setMAGIC_DAMAGE(20);
        containter.setREGENERGY(100);
        containter.setVITALIS(100);
        containter.setAGILITY(100);
        containter.setBLESSING(100);
        containter.setMANA_REGEN(100);
        return containter;
    }

    @Override
    public String getName() {
        return null;
    }

    @NotNull
    @Override
    public EntityState getState() {
        return EntityState.PLAYER;
    }

    @NotNull
    @Override
    public Boolean getPersistent() {
        return false;
    }

    @NotNull
    @Override
    public Skill getExpType() {
        return null;
    }

    @Override
    public double getExpDrop() {
        return 0;
    }

}
