package com.gertoxq.minedom.registry.ability.TriggerFace;

import com.gertoxq.minedom.events.Custom.Events.MagicHitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryDeathEvent;
import com.gertoxq.minedom.registry.player.RegistryPlayer;

public interface MagicHitAbility extends AbilityInterface {
    AbilityAction ability(MagicHitEvent e, RegistryPlayer player);
}
