package com.gertoxq.minedom.registry.ability.abilities.lightning;

import com.gertoxq.minedom.Stats.EntityState;
import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.MeleeHitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.RegistryHitEvent;
import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.ability.interfaces.actionfaces.DamagerAction;
import com.gertoxq.minedom.registry.ability.interfaces.actionfaces.ManaConsumerAction;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.entity.Entity;

import java.util.List;

public class Strike extends AbilityAction implements DamagerAction, ManaConsumerAction {
    public Strike() {
        super(5,"lightning.strike");
    }

    @Override
    public void ability(AEvent e, RegistryPlayer player) {
        MeleeHitEvent event = (MeleeHitEvent) e;
        if (!consume(player)) return;
        RegistryEntity target = event.getEntity();
        List<Entity> entities = target.entity.getNearbyEntities(4,4,4);
        for (RegistryEntity entity: RegistryEntity.filterRegisteredEntities(entities)) {
            if (entity instanceof RegistryPlayer || entity.state == EntityState.INDICATOR) continue;

            doDamage(player, entity, true);

            entity.entity.getLocation().getWorld().strikeLightning(entity.entity.getLocation());
        }
        target.entity.getLocation().getWorld().strikeLightning(target.entity.getLocation());
        doDamage(player, target, true);
    }

    @Override
    public double getDamage() {
        return 20;
    }

    @Override
    public RegistryHitEvent.DamageSource getDamageType() {
        return RegistryHitEvent.DamageSource.MAGIC;
    }

    @Override
    public double getManaCost() {
        return 50;
    }
}
