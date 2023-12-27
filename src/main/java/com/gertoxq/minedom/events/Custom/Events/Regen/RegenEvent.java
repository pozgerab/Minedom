package com.gertoxq.minedom.events.Custom.Events.Regen;

import com.gertoxq.minedom.events.Custom.REvent;
import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.registry.entity.RegistryEntity;

public class RegenEvent extends REvent {
    private final RegenCause cause;
    private double amount;
    private final RegistryEntity healed;
    private RegistryEntity healedBy;
    private boolean cancelled = false;
    public RegenEvent(RegistryEntity healed, double amount, RegenCause cause) {
        this.cause = cause;
        this.amount = amount;
        this.healed = healed;
    }
    public RegenEvent(RegistryEntity player, double amount, RegistryEntity healedBy) {
        this.cause = RegenCause.BY_PLAYER;
        this.amount = amount;
        this.healed = player;
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

    public RegistryEntity getHealed() {
        return healed;
    }

    public RegistryEntity getHealedBy() {
        return healedBy;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public RegistryEntity getTriggerer() {
        return this.healed;
    }

    public enum RegenCause {
        POTION,
        MAGIC,
        NATURAL,
        MANA,
        BY_PLAYER,
        CUSTOM
    }
}
