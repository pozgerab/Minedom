package com.gertoxq.minedom.registry.ability.TriggerFace;

import com.gertoxq.minedom.events.Custom.Events.ProjectileHitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryDeathEvent;
import com.gertoxq.minedom.registry.player.RegistryPlayer;

public interface ProjectileHitAbility extends AbilityInterface {

    AbilityAction ability(ProjectileHitEvent e, RegistryPlayer player);
}
