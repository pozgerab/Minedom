package com.gertoxq.minedom.registry.player;

import com.gertoxq.minedom.StatSystem.EntityState;
import com.gertoxq.minedom.StatSystem.StatSystem;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.skill.Skill;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RegistryPlayer extends RegistryEntity{

    public static ArrayList<RegistryPlayer> players = new ArrayList<>();

    public Player player;
    public HashMap<Stats, Double> equipmentStats;
    public HashMap<Stats, Double> profileStats;
    public HashMap<Stats, Double> abilityStats;
    public HashMap<Stats, Double> stats;
    public UUID uuid;
    public Boolean joined;
    public EntityState state;
    public ArrayList<Ability> abilities;
    public HashMap<Skill, Double> skillLevels;
    public HashMap<Skill, Double> skillExps;

    public RegistryPlayer(Player player) {
        super("player", player);
        this.abilityStats = StatSystem.newEmptyPlayerStats();
        this.abilities = Ability.defaultAbilities;
        this.skillLevels = Skill.getNewSkills();
        this.skillExps = Skill.getNewSkills();
        this.profileStats = setStats();
        this.equipmentStats = StatSystem.newEmptyPlayerStats();
        this.stats = StatSystem.sumStats(this);
        this.uuid = player.getUniqueId();
        this.joined = true;
        this.player = player;
        this.player.setHealthScale(40.0);
        this.state = EntityState.PLAYER;
        entities.add(this);
        players.add(this);
    }

    public void addAbility(Ability ability) {
        this.abilities.add(ability);
    }

    public void addAbility(ArrayList<Ability> abilities) {
        this.abilities.addAll(abilities);
    }

    public static RegistryPlayer getRegistryPlayer(Player player) {

        RegistryPlayer registryPlayer = players.stream().filter(e -> e.uuid.compareTo(player.getUniqueId()) == 0).findAny().orElse(null);
        if (registryPlayer == null) return null;
        registryPlayer.stats = StatSystem.sumStats(registryPlayer);
        Double speed = registryPlayer.stats.get(Stats.AGILITY);
        Double health = registryPlayer.stats.get(Stats.HEALTH);
        registryPlayer.player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        registryPlayer.player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed / 1000);
        return registryPlayer;
    }

    public void updateStats() {
        stats = StatSystem.sumStats(this);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(stats.get(Stats.HEALTH));
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(stats.get(Stats.AGILITY) / 1000);
        ((RegistryEntity)this).stats = stats;
        player.setHealthScale(40.0);
    }

    public void addExp(RegistryEntity killedEntity) {
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
    public EntityType setType() {
        return EntityType.PLAYER;
    }

    @Override
    public HashMap<Stats, Double> setStats() {
        return StatSystem.newPlayerStats(20.0, 0.0, 5.0, 0.0, 100.0, 20.0, 0.0, 1.0, 100.0);
    }

    @Override
    public String setName() {
        return null;
    }

    @Override
    public EntityState setState() {
        return EntityState.PLAYER;
    }

    @Override
    public Boolean setPersistent() {
        return false;
    }

    @Override
    public double setSpawnChance() {
        return 1;
    }

    @Override
    public Skill setExpType() {
        return null;
    }

    @Override
    public double setExpDrop() {
        return 0;
    }

    @Override
    public EntityType setReplacement() {
        return null;
    }

    @Override
    public RegistryEntity setRegistryEntityReplacement() {
        return null;
    }
}
