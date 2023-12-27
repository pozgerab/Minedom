package com.gertoxq.minedom.events.Custom.Events.RegistryHit;

import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import com.gertoxq.minedom.registry.ability.TriggerFace.RecieveHitAbility;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.checkerframework.checker.nullness.qual.NonNull;

public class RecieveHitEvent extends RegistryHitEvent {

    public RecieveHitEvent(RegistryEntity damager, RegistryEntity target, double damage, DamageSource source, boolean lock) {
        super(damager, target, damage, source, lock);
    }

    public RecieveHitEvent(RegistryEntity damager, RegistryEntity target, double damage, DamageSource source) {
        super(damager, target, damage, source);
    }

    @Override
    public RegistryEntity getTriggerer() {
        return this.getEntity();
    }

    @Override
    public @NonNull Class<? extends AbilityInterface> getTriggerFace() {
        return RecieveHitAbility.class;
    }
}
