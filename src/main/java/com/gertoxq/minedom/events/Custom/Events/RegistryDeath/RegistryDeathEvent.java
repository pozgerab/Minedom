package com.gertoxq.minedom.events.Custom.Events.RegistryDeath;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.RegistryHitEvent;
import com.gertoxq.minedom.events.Custom.REvent;
import com.gertoxq.minedom.registry.ability.TriggerFace.DeathAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.checkerframework.checker.nullness.qual.NonNull;

public class RegistryDeathEvent extends REvent implements AEvent {
    private boolean cancelled;
    private final RegistryEntity killer;
    private final RegistryEntity entity;
    private final double damage;
    private final RegistryHitEvent.DamageSource source;

    public RegistryDeathEvent(RegistryEntity entity, RegistryEntity killer, double damage, RegistryHitEvent.DamageSource source) {
        this.entity = entity;
        this.killer = killer;
        this.damage = damage;
        this.source = source;
        this.cancelled = false;
    }

    public RegistryEntity getKiller() {
        return killer;
    }

    public RegistryEntity getEntity() {
        return entity;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public double getDamage() {
        return damage;
    }

    public RegistryHitEvent.DamageSource getSource() {
        return source;
    }

    @Override
    @NonNull
    public Class<? extends AbilityInterface> getTriggerFace() {
        return DeathAbility.class;
    }

    @Override
    public RegistryEntity getTriggerer() {
        return this.entity;
    }
}