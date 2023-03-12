package com.gertoxq.minedom.events.Events;

import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RegistryDeathEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

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
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}