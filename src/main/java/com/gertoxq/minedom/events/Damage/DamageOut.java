package com.gertoxq.minedom.events.Damage;

import com.gertoxq.minedom.Stats.EntityState;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.RegistryHitEvent;
import com.gertoxq.minedom.math.DmgCalc;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Listens to damage events and triggers custom damage events
 */
public class DamageOut implements Listener {
    /**
     * Listens to bukkit damage event and triggers the custom damage events. Cancels the bukkit event, so no knockback is happening
     * @Triggers {@link RegistryEntity#damage(double, RegistryEntity, RegistryHitEvent.DamageSource)}
     * @param e {@link EntityDamageByEntityEvent}
     */
    @EventHandler (priority = EventPriority.LOWEST)
    public void onMeleeDamageOut(EntityDamageByEntityEvent e) {
        switch (e.getCause()) {
            case ENTITY_ATTACK -> {
                Entity victim = e.getEntity();
                RegistryEntity registryVictim = RegistryEntity.getRegistryEntity(victim);
                if (registryVictim == null) return;

                Entity predator = e.getDamager();
                RegistryEntity registryPredator = RegistryEntity.getRegistryEntity(predator);
                if (registryPredator == null) return;
                if (registryPredator.state == EntityState.INDICATOR) return;
                if (registryPredator.state == EntityState.PLAYER) {

                    Double predStr = registryPredator.stats.getSTRENGTH();
                    Double predDmg = registryPredator.stats.getDAMAGE();
                    double finalDmg = DmgCalc.toEntityDmgCalc(predDmg, predStr);
                    registryVictim.damage(finalDmg, registryPredator, RegistryHitEvent.DamageSource.MELEE);

                } else {
                    registryVictim.damage(e.getDamage(), registryPredator, RegistryHitEvent.DamageSource.MELEE);
                }
            }
            case PROJECTILE -> {
                Entity hitEntity = e.getEntity();
                RegistryEntity registryVictim = RegistryEntity.getRegistryEntity(hitEntity);
                if (registryVictim == null) return;

                LivingEntity shooter = (LivingEntity) ((Projectile) e.getDamager()).getShooter();
                if (shooter == null) return;
                RegistryEntity registryPredator = RegistryEntity.getRegistryEntity(shooter);
                if (registryPredator == null) return;
                if (registryPredator.state == EntityState.INDICATOR) return;

                double predStr = registryPredator.stats.getSTRENGTH();
                double predDmg = registryPredator.stats.getDAMAGE();
                double damage = DmgCalc.getMeleeDamage(predDmg, predStr);

                registryVictim.damage(damage, registryPredator, RegistryHitEvent.DamageSource.PROJECTILE);
            }
        }
        e.setDamage(0);
    }


}
