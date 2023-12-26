package com.gertoxq.minedom.math;

import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.RegistryPlayer;

/**
 * Damage Calculations
 */
public class DmgCalc {
    /**
     * Calculates final damage from
     * @param predDmg Attacker damage stat
     * @param victDef Attacked entity def stat
     * @return final damage
     */
    public static double fromEntityDmgCalc(Double predDmg, Double victDef) {
        return (predDmg * getMeleeReduction(victDef));
    }

    public static double getMeleeReduction(double def) {
        return 1 - ((def > 0? def : 0) / ((def > 0? def : 0) + 60));
    }
    public static double getMagicReduction(double magicDef) {
        return 1 - ((magicDef > 0 ? magicDef : 0) / ((magicDef > 0 ? magicDef : 0) + 100));
    }

    public static double getMeleeDamage(double dmg, double str) {
        return ((dmg < 1 ? 1 : dmg)/2) * ((str < 1 ? 1 : str)/2);
    }

    public static double toEntityDmgCalc(Double predDmg, Double predStr) {
        if (predStr < 1) predStr = 1.0;
        if (predDmg < 1) predDmg = 1.0;
        return  (predDmg/2) * (predStr/2);
    }

    public static double fromEntityDmgCalc(RegistryEntity predator, RegistryEntity victim) {
        return fromEntityDmgCalc(predator.stats.getDAMAGE(),victim.stats.getDEFENSE());
    }

    public static double fromEntityDmgCalc(RegistryEntity predator, RegistryPlayer victim) {
        return fromEntityDmgCalc(predator.stats.getDAMAGE(),victim.stats.getDEFENSE());
    }

    public static double toEntityDmgCalc(RegistryPlayer predator) {
        return toEntityDmgCalc(predator.stats.getDAMAGE(),predator.stats.getSTRENGTH());
    }

    public static double getMagicDmg(Double dmg, Double magicDmg, Double mana) {
        return ((dmg < 1 ? 1:dmg)/6) * ((magicDmg < 1?1:magicDmg)/2) * ((mana<1?1:mana)/100);
    }

    public static double reduceMagicDamage(Double damage, Double magicDef) {
        return (damage - damage * (magicDef / (magicDef+100)));
    }

    public static double toEntityMagicDmgCalc(RegistryPlayer predator, RegistryEntity target) {
        return reduceMagicDamage(getMagicDmg(predator.stats.getDAMAGE(), predator.stats.getMAGIC_DAMAGE(), predator.stats.getMANA()), target.stats.getMAGIC_DEFENSE());
    }

    public static double toEntityMagicDmgCalc(Double predDmg, Double predMagicDmg, Double predMana, Double victMagicDef) {
        return reduceMagicDamage(getMagicDmg(predDmg, predMagicDmg, predMana), victMagicDef);
    }

    public static double fromEntityMagicDmgCalc(Double predDmg, Double victMagicDef) {
        return reduceMagicDamage(getMagicDmg(predDmg, 0.0, 0.0), victMagicDef);
    }

}
