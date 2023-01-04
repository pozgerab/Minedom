package com.gertoxq.minedom.events.Damage;

import com.gertoxq.minedom.StatSystem.EntityState;
import com.gertoxq.minedom.StatSystem.Stats;
import com.gertoxq.minedom.math.DmgCalc;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class DamageOut implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onMeleeDamageOut(EntityDamageByEntityEvent e) {
        if (e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;

        Entity victim = e.getEntity();
        RegistryEntity registryVictim = RegistryEntity.getRegistryEntity(victim);
        if (registryVictim == null) return;

        Entity predator = e.getDamager();
        RegistryEntity registryPredator = RegistryEntity.getRegistryEntity(predator);
        if (registryPredator == null) return;
        if (registryPredator.state == EntityState.PLAYER) {

            Double predStr = ((RegistryPlayer) registryPredator).stats.get(Stats.STRENGTH);
            Double predDmg = ((RegistryPlayer) registryPredator).stats.get(Stats.DAMAGE);
            double finalDmg = DmgCalc.toEntityDmgCalc(predDmg,predStr, registryVictim.stats.get(Stats.DEFENSE));
            e.setDamage(finalDmg);

        } else  {
            e.setDamage(DmgCalc.fromEntityDmgCalc(registryPredator.stats.get(Stats.DAMAGE), ((RegistryPlayer)registryVictim).stats.get(Stats.DEFENSE)));
        }

    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onProjectileHit(ProjectileHitEvent e) {

        Entity hitEntity = e.getHitEntity();
        if (hitEntity == null) return;
        RegistryEntity registryVictim = RegistryEntity.getRegistryEntity(hitEntity);
        if (registryVictim == null) return;

        LivingEntity shooter = (LivingEntity) e.getEntity().getShooter();
        if (shooter == null) return;
        RegistryEntity registryPredator = RegistryEntity.getRegistryEntity(shooter);
        if (registryPredator == null) return;
        if (registryPredator.state == EntityState.PLAYER) {

            Double predStr = ((RegistryPlayer) registryPredator).stats.get(Stats.STRENGTH);
            Double predDmg = ((RegistryPlayer) registryPredator).stats.get(Stats.DAMAGE);
            double finalDmg = DmgCalc.toEntityDmgCalc(predDmg,predStr, registryVictim.stats.get(Stats.DEFENSE));
            registryVictim.entity.damage(finalDmg, registryPredator.entity);

        } else {
            registryVictim.entity.damage(DmgCalc.fromEntityDmgCalc(registryPredator.stats.get(Stats.DAMAGE), registryVictim.stats.get(Stats.DEFENSE)));
        }

    }

}
