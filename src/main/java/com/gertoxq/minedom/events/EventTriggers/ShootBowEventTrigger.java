package com.gertoxq.minedom.events.EventTriggers;

import com.gertoxq.minedom.events.Custom.Events.ShootBowEvent;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ShootBowEventTrigger implements Listener {

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        if (e instanceof ShootBowEvent) return;
        RegistryEntity shooter = RegistryEntity.getRegistryEntity(e.getEntity());
        if (shooter == null) {
            e.setCancelled(true);
            return;
        }
        new ShootBowEvent(shooter, e.getBow(), e.getConsumable(), e.getProjectile(), e.getHand(), e.getForce(), e.shouldConsumeItem()).callEvent();
    }
}
