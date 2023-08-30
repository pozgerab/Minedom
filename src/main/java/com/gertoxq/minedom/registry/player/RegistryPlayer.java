package com.gertoxq.minedom.registry.player;

import com.gertoxq.minedom.StatSystem.EntityState;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.action.Statable;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.skill.Skill;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class RegistryPlayer extends RegistryEntity {

    public static ArrayList<RegistryPlayer> players = new ArrayList<>();

    public Player player;
    public HashMap<Stats, Double> handStats;
    public HashMap<Stats, Double> armorStats;
    public HashMap<Stats, Double> profileStats;
    public HashMap<Stats, Double> abilityStats;
    public HashMap<Stats, Double> stats;
    public UUID uuid;
    public Boolean joined;
    public EntityState state;
    public ArrayList<Ability> abilities;
    public HashMap<Skill, Double> skillLevels;
    public HashMap<Skill, Double> skillExps;

    private final HashMap<EquipmentSlot, List<Ability>> activeEquipmentAbilities = new HashMap<>();
    private final List<Ability> activeFullSetAbility = new ArrayList<>();

    /**
     * Init of Player
     * @param player Bukkit Player
     */
    public RegistryPlayer(Player player) {
        super("player", player);
        this.abilityStats = Stats.newEmptyPlayerStats();
        this.abilities = Ability.defaultAbilities;
        this.skillLevels = Skill.getNewSkills();
        this.skillExps = Skill.getNewSkills();
        this.profileStats = setStats();
        this.armorStats = Stats.newEmptyPlayerStats();
        this.handStats = Stats.newEmptyPlayerStats();
        this.stats = Stats.sumStats(this);
        this.uuid = player.getUniqueId();
        this.joined = true;
        this.player = player;
        this.player.setHealthScale(40.0);
        this.state = EntityState.PLAYER;
        Arrays.stream(EquipmentSlot.values()).forEach(equipmentSlot -> {
            activeEquipmentAbilities.put(equipmentSlot, new ArrayList<>());
        });
        players.add(this);
    }

    /**
     * Gets the player from Bukkit player
     * @param player Bukkit Player
     * @return Registry Player
     */

    public static RegistryPlayer getRegistryPlayer(Player player) {

        RegistryPlayer registryPlayer = players.stream().filter(e -> e.uuid.compareTo(player.getUniqueId()) == 0).findAny().orElse(null);
        if (registryPlayer == null) return null;
        registryPlayer.stats = Stats.sumStats(registryPlayer);
        Double speed = registryPlayer.stats.get(Stats.AGILITY);
        Double health = registryPlayer.stats.get(Stats.HEALTH);
        registryPlayer.player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        registryPlayer.player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed / 1000);
        return registryPlayer;
    }

    /**
     * Get active equipment abilitites
     * @return Ability map
     */
    public HashMap<EquipmentSlot, List<Ability>> getActiveEquipmentAbilities() {
        return activeEquipmentAbilities;
    }

    /**
     * Gets active full set abilities
     * @return Ability List
     */
    public List<Ability> getActiveFullSetAbility() {
        return activeFullSetAbility;
    }

    /**
     * Adds active equipment ability
     * @param slot Responsible slot for ability
     * @param ability Ability
     */
    public void addActiveAbility(EquipmentSlot slot, Ability ability) {
        activeEquipmentAbilities.get(slot).add(ability);
    }

    /**
     * Clears active equipment abilities for one slot
     * @param slot Responsible slot
     */
    public void clearActiveAbility(EquipmentSlot slot) {
        List<Ability> abilities = activeEquipmentAbilities.get(slot);
        abilities.forEach(ability -> {
            player.sendMessage(String.valueOf(ability.getActions().size()));
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
    public void addFullSetAbility(Ability ability) {
        activeFullSetAbility.add(ability);
    }

    /**
     * Clears active full set abilities
     */
    public void clearFullSetAbility() {
        activeFullSetAbility.forEach(ability -> {
            ability.getActions().forEach(action -> {
                if (action instanceof Statable stateAction) {
                    player.sendMessage("clean");
                    stateAction.cleanUp(this);
                }
            });
        });
        activeFullSetAbility.clear();
    }

    /**
     * Refreshes the player's stats
     */
    public void updateStats() {
        stats = Stats.sumStats(this);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(stats.get(Stats.HEALTH));
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(stats.get(Stats.AGILITY) / 1000);
        player.setHealthScale(40.0);
    }

    /**
     * Adds and displays exp from a killed entity
     * @param killedEntity Registry entity
     */
    public void addExp(RegistryEntity killedEntity) {
        if (killedEntity.expType == null) return;
        Skill.addSkillExp(killedEntity.expType, this, killedEntity.expDrop);
        this.player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+" + killedEntity.expDrop + " " + killedEntity.expType.displayName + " exp"));
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
        this.player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "+" + amount + " " + skill.displayName + " exp"));
        double newExp = this.skillExps.get(skill) + amount;
        this.skillExps.put(skill, newExp);
        double newLevel = Skill.CalcLvlFromEXP(skill, newExp);
        if (newLevel > this.skillLevels.get(skill)) {
            this.skillLevels.put(skill, newLevel);
            this.player.sendMessage(ChatColor.AQUA + "SKILL LEVEL UP");
            this.player.sendMessage(ChatColor.GOLD + skill.displayName + " " + (int) newLevel);
        }
    }


    @Override
    public @NonNull Material asItem() {
        return Material.PLAYER_HEAD;
    }

    @Override
    public @NonNull EntityType setType() {
        return EntityType.PLAYER;
    }

    @Override
    public @NonNull HashMap<Stats, Double> setStats() {
        return Stats.newPlayerStats(20.0, 0.0, 5.0, 0.0, 100.0, 20.0, 0.0, 100.0, 100.0, 100.0, 100.0);
    }

    @Override
    public String setName() {
        return null;
    }

    @Override
    public @NonNull EntityState setState() {
        return EntityState.PLAYER;
    }

    @Override
    public @NonNull Boolean setPersistent() {
        return false;
    }

    @Override
    public @NonNull Skill setExpType() {
        return null;
    }

    @Override
    public double setExpDrop() {
        return 0;
    }

}
