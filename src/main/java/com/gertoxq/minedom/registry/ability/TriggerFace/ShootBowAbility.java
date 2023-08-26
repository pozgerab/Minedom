package com.gertoxq.minedom.registry.ability.TriggerFace;

import com.gertoxq.minedom.events.Custom.Events.RegistryDeathEvent;
import com.gertoxq.minedom.events.Custom.Events.ShootBowEvent;
import com.gertoxq.minedom.registry.player.RegistryPlayer;

public interface ShootBowAbility extends AbilityInterface {
    AbilityAction ability(ShootBowEvent e, RegistryPlayer player);
}
