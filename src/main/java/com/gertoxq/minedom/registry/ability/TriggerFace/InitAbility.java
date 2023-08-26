package com.gertoxq.minedom.registry.ability.TriggerFace;

import com.gertoxq.minedom.events.Custom.Events.InitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryDeathEvent;
import com.gertoxq.minedom.registry.player.RegistryPlayer;

public interface InitAbility extends AbilityInterface {
    AbilityInterface.AbilityAction ability(InitEvent e, RegistryPlayer player);
}
