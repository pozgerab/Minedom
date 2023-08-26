package com.gertoxq.minedom.registry.ability.TriggerFace;

import com.gertoxq.minedom.events.Custom.Events.RegistryDeathEvent;
import com.gertoxq.minedom.registry.player.RegistryPlayer;

public interface DeathAbility extends AbilityInterface {
     AbilityAction ability(RegistryDeathEvent e, RegistryPlayer player);
}


