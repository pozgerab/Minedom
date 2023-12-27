package com.gertoxq.minedom.events.Custom;

import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Used to create a custom event. Can extend a Bukkit Event.
 */
public abstract class REvent extends Event {
    /**
     * Handler list
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * @return The handler list
     */
    @Override
    public @NonNull HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Bukkit event requirement
     * @return The handler list
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public abstract RegistryEntity getTriggerer();
}
