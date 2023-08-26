package com.gertoxq.minedom.registry.ability.TriggerFace;

import com.gertoxq.minedom.events.Custom.Events.MeleeHitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryDeathEvent;
import com.gertoxq.minedom.registry.player.RegistryPlayer;

public interface MeleeHitAbility extends AbilityInterface {
    AbilityAction ability(MeleeHitEvent e, RegistryPlayer player);
}
