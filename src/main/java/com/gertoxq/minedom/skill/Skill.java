package com.gertoxq.minedom.skill;

import com.gertoxq.minedom.registry.player.RegistryPlayer;

import java.util.ArrayList;
import java.util.HashMap;

public enum Skill {

    COMBAT("combat", "Combat"),
    GARDENING("gardening", "Gardening"),
    ARCHERY("archery", "Archery"),
    WOODCUTTING("woodcutting", "Woodcutting"),
    CHEMISTRY("chemistry", "Chemistry"),
    MAGIC("magic", "Magic");

    final ArrayList<Double> requiredExp;
    final ArrayList<Double> allRequiredExp;
    final int maxLvl;
    final public String name;
    final public String displayName;

    Skill(String name, String displayName) {
        this.displayName = displayName;
        this.name = name;
        this.requiredExp = newRequiredExp();
        this.allRequiredExp = newAllRequiredEXP();
        this.maxLvl = 100;
    }

    public static void addSkillExp(Skill skill, RegistryPlayer player, Double exp) {
        Double newExp = player.skillExps.get(skill) + exp;
        player.skillExps.put(skill, newExp);
        player.skillLevels.put(skill, (double) CalcLvlFromEXP(skill, newExp));
    }

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

    public static ArrayList<Double> newAllRequiredEXP() {
        ArrayList<Double> allRequiredExps = new ArrayList<>();
        int allExp = 0;
        allRequiredExps.add((double) allExp);
        for (int i = 0; i < 5; i++) {
            allExp += 200;
            allRequiredExps.add((double) allExp);
        }
        for (int i = 0; i < 20; i++) {
            allExp += 1000.0;
            allRequiredExps.add((double) allExp);
        }
        for (int i = 0; i < 25; i++) {
            allExp += 10000.0;
            allRequiredExps.add((double) allExp);
        }
        for (int i = 0; i < 25; i++) {
            allExp += 100000.0;
            allRequiredExps.add((double) allExp);
        }
        for (int i = 0; i < 25; i++) {
            allExp += 1000000.0;
            allRequiredExps.add((double) allExp);
        }
        return allRequiredExps;
    }

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

    public static HashMap<Skill, Double> getNewSkills() {
        HashMap<Skill, Double> skills = new HashMap<>();
        for (Skill skill : Skill.values()) {
            skills.put(skill, 0.0);
        }
        return skills;
    }

}
