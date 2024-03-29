package com.gertoxq.minedom.events.Custom.Events.RegistryHit;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.RegistryHitEvent;
import com.gertoxq.minedom.events.Custom.REvent;
import com.gertoxq.minedom.registry.ability.TriggerFace.MeleeHitAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.checkerframework.checker.nullness.qual.NonNull;

public class MeleeHitEvent extends RegistryHitEvent implements AEvent {

    public MeleeHitEvent(RegistryEntity damager, RegistryEntity target, double damage, boolean lock) {
        super(damager, target, damage, DamageSource.MELEE, lock);
    }

    public MeleeHitEvent(RegistryEntity damager, RegistryEntity target, double damage) {
        super(damager, target, damage, DamageSource.MELEE);
    }

    @Override
    @NonNull
    public Class<? extends AbilityInterface> getTriggerFace() {
        return MeleeHitAbility.class;
    }
}
