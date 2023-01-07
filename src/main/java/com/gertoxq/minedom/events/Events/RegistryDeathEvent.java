package com.gertoxq.minedom.events.Events;

import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RegistryDeathEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;
    private final RegistryEntity killer;
    private final RegistryEntity entity;

    public RegistryDeathEvent(RegistryEntity entity, RegistryEntity killer) {
        this.entity = entity;
        this.killer = killer;
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

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}