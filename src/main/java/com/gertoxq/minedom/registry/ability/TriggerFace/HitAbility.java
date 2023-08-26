package com.gertoxq.minedom.registry.ability.TriggerFace;

import com.gertoxq.minedom.events.Custom.Events.RegistryHitEvent;
import com.gertoxq.minedom.registry.player.RegistryPlayer;

public interface HitAbility extends AbilityInterface {
    AbilityAction ability(RegistryHitEvent e, RegistryPlayer player);
}
