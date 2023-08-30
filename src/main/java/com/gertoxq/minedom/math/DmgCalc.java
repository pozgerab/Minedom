package com.gertoxq.minedom.math;

import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.player.RegistryPlayer;

/**
 * Damage Calculations
 */
public class DmgCalc {

    public static double fromEntityDmgCalc(Double predDmg, Double victDef) {
        return (predDmg-predDmg*(victDef/(victDef+60)));
    }

    public static double toEntityDmgCalc(Double predDmg, Double predStr, Double victDef) {
        if (predStr < 1) predStr = 1.0;
        if (predDmg < 1) predDmg = 1.0;
        double rawDmg = (predDmg/2) * (predStr/2);
        return fromEntityDmgCalc(rawDmg, victDef);
    }

    public static double fromEntityDmgCalc(RegistryEntity predator, RegistryEntity victim) {
        return fromEntityDmgCalc(predator.stats.get(Stats.DAMAGE),victim.stats.get(Stats.DEFENSE));
    }

    public static double fromEntityDmgCalc(RegistryEntity predator, RegistryPlayer victim) {
        return fromEntityDmgCalc(predator.stats.get(Stats.DAMAGE),victim.stats.get(Stats.DEFENSE));
    }

    public static double toEntityDmgCalc(RegistryPlayer predator, RegistryEntity victim) {
        return toEntityDmgCalc(predator.stats.get(Stats.DAMAGE),predator.stats.get(Stats.STRENGTH),victim.stats.get(Stats.DEFENSE));
    }

    public static double calcMagicDmg(Double dmg, Double magicDmg, Double mana) {
        if (dmg < 1) dmg = 1.0;
        if (magicDmg < 1) magicDmg = 1.0;
        if (mana < 1) mana = 1.0;
        return (dmg/6) * (magicDmg/2) * (mana/100);
    }

    public static double reduceMagicDamage(Double damage, Double magicDef) {
        return (damage - damage * (magicDef / (magicDef+100)));
    }

    public static double toEntityMagicDmgCalc(RegistryPlayer predator, RegistryEntity target) {
        return reduceMagicDamage(calcMagicDmg(predator.stats.get(Stats.DAMAGE), predator.stats.get(Stats.MAGIC_DAMAGE), predator.stats.get(Stats.MANA)), target.stats.get(Stats.MAGIC_DEFENSE));
    }

    public static double toEntityMagicDmgCalc(Double predDmg, Double predMagicDmg, Double predMana, Double victMagicDef) {
        return reduceMagicDamage(calcMagicDmg(predDmg, predMagicDmg, predMana), victMagicDef);
    }

    public static double fromEntityMagicDmgCalc(Double predDmg, Double victMagicDef) {
        return reduceMagicDamage(calcMagicDmg(predDmg, 0.0, 0.0), victMagicDef);
    }

}
