package com.gertoxq.minedom.events.Custom.Events.Init;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.REvent;
import com.gertoxq.minedom.registry.ability.TriggerFace.InitAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.jetbrains.annotations.NotNull;

public class InitEvent extends REvent implements AEvent {

    private final RegistryPlayer player;
    public InitEvent(RegistryPlayer player) {
        this.player = player;
    }

    public RegistryPlayer getPlayer() {
        return player;
    }

    @Override
    public @NotNull Class<? extends AbilityInterface> getTriggerFace() {
        return InitAbility.class;
    }

    @Override
    public RegistryPlayer getTriggerer() {
        return this.player;
    }
}
