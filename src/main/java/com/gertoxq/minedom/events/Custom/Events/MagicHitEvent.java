package com.gertoxq.minedom.events.Custom.Events;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.REvent;
import com.gertoxq.minedom.registry.ability.TriggerFace.MagicHitAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.checkerframework.checker.nullness.qual.NonNull;

public class MagicHitEvent extends REvent implements AEvent {

    private final RegistryEntity entity;
    private final RegistryEntity damager;
    private double damage;
    private final boolean lock;
    private boolean cancelled;

    public MagicHitEvent(RegistryEntity damager, RegistryEntity target, double damage, boolean lock) {
        this.damager = damager;
        this.entity = target;
        this.damage = damage;
        this.lock = lock;
    }
    public MagicHitEvent(RegistryEntity damager, RegistryEntity target, double damage) {
        this.damager = damager;
        this.entity = target;
        this.damage = damage;
        this.lock = false;
    }

    public RegistryEntity getEntity() {
        return entity;
    }

    public RegistryEntity getDamager() {
        return damager;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public boolean isLock() {
        return lock;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    @NonNull
    public Class<? extends AbilityInterface> getTriggerFace() {
        return MagicHitAbility.class;
    }
}
