package com.gertoxq.minedom.events.Events;

import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RegistryHitEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final RegistryEntity target;
    private final RegistryEntity damager;
    private double damage;
    private final boolean lock;
    private boolean cancelled;
    private final DamageSource source;

    public RegistryHitEvent(RegistryEntity damager, RegistryEntity target, double damage, DamageSource source, boolean lock) {
        this.damager = damager;
        this.target = target;
        this.damage = damage;
        this.lock = lock;
        this.source = source;
        this.cancelled = false;
    }
    public RegistryHitEvent(RegistryEntity damager, RegistryEntity target, double damage, DamageSource source) {
        this.damager = damager;
        this.target = target;
        this.damage = damage;
        this.source = source;
        this.lock = false;
        this.cancelled = false;
    }

    public RegistryEntity getTarget() {
        return target;
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

    public DamageSource getSource() {
        return source;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public enum DamageSource {
        MELEE,
        MAGIC,
        PROJECTILE,
        CUSTOM
    }

}
