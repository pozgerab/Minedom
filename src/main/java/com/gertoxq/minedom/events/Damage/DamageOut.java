package com.gertoxq.minedom.events.Damage;

import com.gertoxq.minedom.StatSystem.EntityState;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.events.Custom.Events.RegistryHitEvent;
import com.gertoxq.minedom.math.DmgCalc;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Listens to damage events and triggers custom damage events
 */
public class DamageOut implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onMeleeDamageOut(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
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

                    Double predStr = ((RegistryPlayer) registryPredator).stats.get(Stats.STRENGTH);
                    Double predDmg = ((RegistryPlayer) registryPredator).stats.get(Stats.DAMAGE);
                    double finalDmg = DmgCalc.toEntityDmgCalc(predDmg, predStr, registryVictim.stats.get(Stats.DEFENSE));
                    registryVictim.damage(finalDmg, registryPredator, RegistryHitEvent.DamageSource.MELEE);

                } else {
                    registryVictim.damage(DmgCalc.fromEntityDmgCalc(registryPredator.stats.get(Stats.DAMAGE), registryVictim.stats.get(Stats.DEFENSE)), registryPredator, RegistryHitEvent.DamageSource.MELEE);
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
                if (registryPredator.state == EntityState.PLAYER) {

                    Double predStr = ((RegistryPlayer) registryPredator).stats.get(Stats.STRENGTH);
                    Double predDmg = ((RegistryPlayer) registryPredator).stats.get(Stats.DAMAGE);
                    double finalDmg = DmgCalc.toEntityDmgCalc(predDmg,predStr, registryVictim.stats.get(Stats.DEFENSE));
                    registryVictim.damage(finalDmg, registryPredator, RegistryHitEvent.DamageSource.PROJECTILE);

                } else {
                    registryVictim.damage(DmgCalc.fromEntityDmgCalc(registryPredator.stats.get(Stats.DAMAGE), registryVictim.stats.get(Stats.DEFENSE)), registryPredator, RegistryHitEvent.DamageSource.PROJECTILE);
                }
            }
        }
    }


}
