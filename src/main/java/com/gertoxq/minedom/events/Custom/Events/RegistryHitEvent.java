package com.gertoxq.minedom.events.Custom.Events;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.REvent;
import com.gertoxq.minedom.registry.ability.TriggerFace.HitAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.checkerframework.checker.nullness.qual.NonNull;

public class RegistryHitEvent extends REvent implements AEvent {

    private final RegistryEntity entity;
    private final RegistryEntity damager;
    private double damage;
    private final boolean lock;
    private boolean cancelled;
    private final DamageSource source;
    private boolean deadly = false;
    private RegistryDeathEvent deathEvent = null;

    public RegistryHitEvent(RegistryEntity damager, RegistryEntity target, double damage, DamageSource source, boolean lock) {
        this.damager = damager;
        this.entity = target;
        this.damage = damage;
        this.lock = lock;
        this.source = source;
        this.cancelled = false;
    }
    public RegistryHitEvent(RegistryEntity damager, RegistryEntity target, double damage, DamageSource source) {
        this.damager = damager;
        this.entity = target;
        this.damage = damage;
        this.source = source;
        this.lock = false;
        this.cancelled = false;
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

    public boolean isDeadly() {
        return deadly;
    }

    public void setDeadly(boolean deadly) {
        this.deadly = deadly;
    }

    public RegistryDeathEvent getDeathEvent() {
        return deathEvent;
    }

    public void setDeathEvent(RegistryDeathEvent deathEvent) {
        this.deathEvent = deathEvent;
    }

    public DamageSource getSource() {
        return source;
    }

    @Override
    @NonNull
    public Class<? extends AbilityInterface> getTriggerFace() {
        return HitAbility.class;
    }

    public enum DamageSource {
        MELEE,
        MAGIC,
        PROJECTILE,
        EXPLOSION,
        CUSTOM
    }

}
