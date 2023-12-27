package com.gertoxq.minedom.events.Custom.Events.RegistryDeath;

import com.gertoxq.minedom.events.Custom.Events.RegistryHit.RegistryHitEvent;
import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import com.gertoxq.minedom.registry.ability.TriggerFace.KillAbility;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.checkerframework.checker.nullness.qual.NonNull;

public class KillEvent extends RegistryDeathEvent {
    public KillEvent(RegistryEntity entity, RegistryEntity killer, double damage, RegistryHitEvent.DamageSource source) {
        super(entity, killer, damage, source);
    }

    @Override
    public RegistryEntity getTriggerer() {
        return this.getKiller();
    }

    @Override
    public @NonNull Class<? extends AbilityInterface> getTriggerFace() {
        return KillAbility.class;
    }
}
