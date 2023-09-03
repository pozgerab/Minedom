package com.gertoxq.minedom.skill;

import com.gertoxq.minedom.registry.RegistryPlayer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * List of skills
 */
public enum Skill {

    COMBAT("combat", "Combat"),
    GARDENING("gardening", "Gardening"),
    ARCHERY("archery", "Archery"),
    WOODCUTTING("woodcutting", "Woodcutting"),
    CHEMISTRY("chemistry", "Chemistry"),
    MAGIC("magic", "Magic");

    /**
     * Required exp amount to level up for each level
     */
    final ArrayList<Double> requiredExp;
    /**
     * Required exp amount to max the skill
     */
    final ArrayList<Double> allRequiredExp;
    /**
     * Max skill level
     */
    final int maxLvl;
    /**
     * Skill identifier
     */
    final public String name;
    /**
     * Skill display name
     */
    final public String displayName;

    /**
     * Init of new skill
     * @param name Skill id
     * @param displayName Skill display name
     */
    Skill(String name, String displayName) {
        this.displayName = displayName;
        this.name = name;
        this.requiredExp = newRequiredExp();
        this.allRequiredExp = newAllRequiredEXP();
        this.maxLvl = 100;
    }

    /**
     * Adds skill exp to player
     * @param skill Skill type
     * @param player Player to gain exp
     * @param exp Exp amount
     */
    public static void addSkillExp(Skill skill, RegistryPlayer player, Double exp) {
        Double newExp = player.skillExps.get(skill) + exp;
        player.skillExps.put(skill, newExp);
        player.skillLevels.put(skill, CalcLvlFromEXP(skill, newExp));
    }

    /**
     * Calculates the skill level by overall exp
     * @param skill Skill type
     * @param exp Exp amount
     * @return Overall skill level
     */
    public static double CalcLvlFromEXP(Skill skill, Double exp) {
        double lvl = 0;
        for (int i =0; i < skill.maxLvl; i++) {
            Double num = skill.allRequiredExp.get(i);
            int newDiff = (int) (exp - num);
            if (newDiff < 0) break;
            lvl = i;
        }
        return lvl;
    }

    /**
     * Overall exp amounts for each level
     * @return List of overall exp amount for each level
     */
    public static ArrayList<Double> newAllRequiredEXP() {
        ArrayList<Double> allRequiredExps = new ArrayList<>();
        int allExp = 0;
        allRequiredExps.add((double) allExp);
        for (int i = 0; i < 5; i++) {
            allExp += 200;
            allRequiredExps.add((double) allExp);
        }
        for (int i = 0; i < 20; i++) {
            allExp += 1000;
            allRequiredExps.add((double) allExp);
        }
        for (int i = 0; i < 25; i++) {
            allExp += 10000;
            allRequiredExps.add((double) allExp);
        }
        for (int i = 0; i < 25; i++) {
            allExp += 100000;
            allRequiredExps.add((double) allExp);
        }
        for (int i = 0; i < 25; i++) {
            allExp += 1000000;
            allRequiredExps.add((double) allExp);
        }
        return allRequiredExps;
    }

    /**
     * Required exp amount needed to level up for each level
     * @return List of exp amounts needed to level up for each level
     */
    public static ArrayList<Double> newRequiredExp() {
        ArrayList<Double> requiredExps = new ArrayList<>();
        requiredExps.add(0.0);
        for (int i = 0; i < 5; i++) {
            requiredExps.add(200.0);
        }
        for (int i = 0; i < 20; i++) {
            requiredExps.add(1000.0);
        }
        for (int i = 0; i < 25; i++) {
            requiredExps.add(10000.0);
        }
        for (int i = 0; i < 25; i++) {
            requiredExps.add(100000.0);
        }
        for (int i = 0; i < 25; i++) {
            requiredExps.add(1000000.0);
        }
        return requiredExps;
    }

    /**
     * @return New empty skill exp container
     */
    public static HashMap<Skill, Double> newEmptySkills() {
        HashMap<Skill, Double> skills = new HashMap<>();
        for (Skill skill : Skill.values()) {
            skills.put(skill, 0.0);
        }
        return skills;
    }

}
