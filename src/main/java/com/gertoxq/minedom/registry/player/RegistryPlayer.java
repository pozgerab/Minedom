package com.gertoxq.minedom.registry.player;

import com.gertoxq.minedom.StatSystem.EntityState;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import com.gertoxq.minedom.registry.ability.TriggerFace.DeathAbility;
import com.gertoxq.minedom.registry.ability.abilities.Lightning;
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

    public HashMap<EquipmentSlot, List<Ability>> activeEquipmentAbilities = new HashMap<>();
    public List<Ability> activeFullsetAbility = new ArrayList<>();

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

    public void addActiveAbility(EquipmentSlot slot, Ability ability) {
        activeEquipmentAbilities.get(slot).add(ability);
    }
    public void clearActiveAbility(EquipmentSlot slot) {
        activeEquipmentAbilities.get(slot).clear();
    }
    public void addFullSetAbility(Ability ability) {
        activeFullsetAbility.add(ability);
    }

    public void updateStats() {
        stats = Stats.sumStats(this);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(stats.get(Stats.HEALTH));
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(stats.get(Stats.AGILITY) / 1000);
        player.setHealthScale(40.0);
    }

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
