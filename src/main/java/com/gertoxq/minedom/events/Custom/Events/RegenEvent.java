package com.gertoxq.minedom.events.Custom.Events;

import com.gertoxq.minedom.events.Custom.REvent;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RegenEvent extends REvent {
    private final RegenCause cause;
    private double amount;
    private final RegistryPlayer player;
    private RegistryPlayer healedBy;
    private boolean cancelled;
    public RegenEvent(RegistryPlayer player, double amount, RegenCause cause) {
        this.cause = cause;
        this.amount = amount;
        this.player = player;
    }
    public RegenEvent(RegistryPlayer player, double amount, RegistryPlayer healedBy) {
        this.cause = RegenCause.BY_PLAYER;
        this.amount = amount;
        this.player = player;
        this.healedBy = healedBy;
    }

    public RegenCause getCause() {
        return cause;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public RegistryPlayer getPlayer() {
        return player;
    }

    public RegistryPlayer getHealedBy() {
        return healedBy;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public enum RegenCause {
        POTION,
        MAGIC,
        NATURAL,
        BY_PLAYER,
        CUSTOM
    }
}
