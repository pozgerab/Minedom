package com.gertoxq.minedom.util;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.HashMap;

import static com.gertoxq.minedom.Stats.Stat.*;

public class StatContainter extends HashMap<Stat, Double> {

    private RegistryPlayer owner;
    public StatContainter() {
        Arrays.stream(Stat.values()).forEach(stat -> this.put(stat, 0.0));
    }

    @Override
    public Double put(Stat key, Double value) {
        if (owner != null && owner.loaded) owner.updateStats();
        return super.put(key, value);
    }

    public StatContainter(RegistryPlayer owner) {
        this.owner = owner;
        Arrays.stream(Stat.values()).forEach(stat -> this.put(stat, 0.0));
    }

    public void increase(Stat stat, double amount) {
        this.put(stat, this.get(stat) + amount);
    }

    public void increaseForTick(Stat stat, double amount, long ticks) {
        this.increase(stat, amount);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> this.increase(stat, -amount), ticks);
    }

    public double getDAMAGE() {
        return this.get(DAMAGE);
    }

    public void setDAMAGE(double DAMAGE) {
        this.put(Stat.DAMAGE, DAMAGE);
    }

    public double getSTRENGTH() {
        return this.get(STRENGTH);
    }

    public void setSTRENGTH(double STRENGTH) {
        this.put(Stat.STRENGTH, STRENGTH);
    }

    public double getMANA() {
        return this.get(MANA);
    }

    public void setMANA(double MANA) {
        this.put(Stat.MANA, MANA);
    }

    public double getMAGIC_DAMAGE() {
        return this.get(MAGIC_DAMAGE);
    }

    public void setMAGIC_DAMAGE(double MAGIC_DAMAGE) {
        this.put(Stat.MAGIC_DAMAGE, MAGIC_DAMAGE);
    }

    public double getDEFENSE() {
        return this.get(DEFENSE);
    }

    public void setDEFENSE(double DEFENSE) {
        this.put(Stat.DEFENSE, DEFENSE);
    }

    public double getMAGIC_DEFENSE() {
        return this.get(MAGIC_DEFENSE);
    }

    public void setMAGIC_DEFENSE(double MAGIC_DEFENSE) {
        this.put(Stat.MAGIC_DEFENSE, MAGIC_DEFENSE);
    }

    public double getHEALTH() {
        return this.get(HEALTH);
    }

    public void setHEALTH(double HEALTH) {
        this.put(Stat.HEALTH, HEALTH);
    }

    public double getREGENERGY() {
        return this.get(REGENERGY);
    }

    public void setREGENERGY(double REGENERGY) {
        this.put(Stat.REGENERGY, REGENERGY);
    }

    public double getVITALIS() {
        return this.get(VITALIS);
    }

    public void setVITALIS(double VITALIS) {
        this.put(Stat.VITALIS, VITALIS);
    }

    public double getAGILITY() {
        return this.get(AGILITY);
    }

    public void setAGILITY(double AGILITY) {
        this.put(Stat.AGILITY, AGILITY);
    }

    public double getBLESSING() {
        return this.get(BLESSING);
    }

    public void setBLESSING(double BLESSING) {
        this.put(Stat.BLESSING, BLESSING);
    }

    public double getMANA_REGEN() {
        return this.get(MANA_REGEN);
    }

    public void setMANA_REGEN(double MANA_REGEN) {
        this.put(Stat.MANA_REGEN, MANA_REGEN);
    }

    public double getATTACK_SPEED() {
        return this.get(ATTACK_SPEED);
    }

    public void setATTACK_SPEED(double ATTACK_SPEED) {
        this.put(Stat.ATTACK_SPEED, ATTACK_SPEED);
    }
}
