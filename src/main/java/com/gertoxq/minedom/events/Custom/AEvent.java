package com.gertoxq.minedom.events.Custom;

import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import org.jetbrains.annotations.NotNull;

public interface AEvent {

    @NotNull Class<? extends AbilityInterface> getTriggerFace();
}
