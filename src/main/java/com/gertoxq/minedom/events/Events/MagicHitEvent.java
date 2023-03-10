package com.gertoxq.minedom.events.Events;

import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class MagicHitEvent extends Event implements Listener {

    private static final HandlerList handlers = new HandlerList();

    private final RegistryEntity target;
    private final RegistryEntity damager;
    private double damage;
    private final boolean lock;
    private boolean cancelled;

    public MagicHitEvent(RegistryEntity damager, RegistryEntity target, double damage, boolean lock) {
        this.damager = damager;
        this.target = target;
        this.damage = damage;
        this.lock = lock;
    }
    public MagicHitEvent(RegistryEntity damager, RegistryEntity target, double damage) {
        this.damager = damager;
        this.target = target;
        this.damage = damage;
        this.lock = false;
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

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
