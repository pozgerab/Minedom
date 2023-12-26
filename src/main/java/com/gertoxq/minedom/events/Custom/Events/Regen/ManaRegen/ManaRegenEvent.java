package com.gertoxq.minedom.events.Custom.Events.Regen.ManaRegen;

import com.gertoxq.minedom.events.Custom.REvent;
import com.gertoxq.minedom.registry.RegistryPlayer;

public class ManaRegenEvent extends REvent {

    private double amount;
    private final RegistryPlayer regened;
    private boolean cancelled = false;

    public ManaRegenEvent(RegistryPlayer regened, double amount) {
        this.regened = regened;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public RegistryPlayer getRegened() {
        return regened;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
