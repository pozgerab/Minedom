package com.gertoxq.minedom.registry.ability.TriggerFace;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.registry.player.RegistryPlayer;

/**
 * Superinterface of ability interfaces
 */
public interface AbilityInterface {

    static DeathAbility getDeath(AbilityInterface interfacee) {
        if (interfacee instanceof DeathAbility ability) return ability;
        return null;
    }
    static HitAbility getHit(AbilityInterface interfacee) {
        if (interfacee instanceof HitAbility ability) return ability;
        return null;
    }
    static InitAbility getInit(AbilityInterface interfacee) {
        if (interfacee instanceof InitAbility ability) return ability;
        return null;
    }

    static MagicHitAbility getMagicHit(AbilityInterface interfacee) {
        if (interfacee instanceof MagicHitAbility ability) return ability;
        return null;
    }
    static MeleeHitAbility getMeleeHit(AbilityInterface interfacee) {
        if (interfacee instanceof MeleeHitAbility ability) return ability;
        return null;
    }

    static ProjectileHitAbility getProjectileHit(AbilityInterface interfacee) {
        if (interfacee instanceof ProjectileHitAbility ability) return ability;
        return null;
    }

    static ShootBowAbility getShootBow(AbilityInterface interfacee) {
        if (interfacee instanceof ShootBowAbility ability) return ability;
        return null;
    }
}
