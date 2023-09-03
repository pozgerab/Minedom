package com.gertoxq.minedom.registry.ability.abilities;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.ProjectileHitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHitEvent;
import com.gertoxq.minedom.events.Custom.Events.ShootBowEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.ability.TriggerFace.HitAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.ProjectileHitAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.ShootBowAbility;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Overwhelm extends Ability implements HitAbility, ProjectileHitAbility, ShootBowAbility {

    @Override
    public String getName() {
        return "Overwhelm";
    }

    @Override
    public String getId() {
        return "overwhelm";
    }

    @Override
    public double getBaseDamage() {
        return 0.0;
    }

    @Override
    public AbilityState getState() {
        return AbilityState.ACTIVE;
    }

    @Override
    public int getCooldown() {
        return 0;
    }

    @Override
    public TriggerType getTriggerType() {
        return TriggerType.MAINHAND;
    }

    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+"Shoot in a straight line.");
        lore.add(ChatColor.GRAY+"On hitting en enemy, blind and froze them for 3s.");
        lore.add(ChatColor.DARK_GRAY +"If enemy is closer than 20 blocks,");
        lore.add(ChatColor.DARK_GRAY+"reduce final damage by"+ ChatColor.RED +" 80%");
        return lore;
    }

    @Override
    public boolean getHasRequirement() {
        return false;
    }

    @Override
    public Skill getRequirementType() {
        return null;
    }

    @Override
    public int getRequirementLevel() {
        return 0;
    }

    @Override
    public AbilityAction ability(HitAbility classs) {
        return new AbilityAction("overwhelm_onhit") {
            @Override
            public void ability(AEvent e, RegistryPlayer player) {
                RegistryHitEvent event = (RegistryHitEvent) e;
                if (event.getEntity().entity.getLocation().distance(player.player.getLocation()) < 20) {
                    event.setDamage(event.getDamage()*0.2);
                }
            }
        };
    }

    @Override
    public AbilityAction ability(ProjectileHitAbility classs) {
        return new AbilityAction("overwhelm_onarrowhit") {
            @Override
            public void ability(AEvent e, RegistryPlayer player) {
                ProjectileHitEvent event = (ProjectileHitEvent) e;
                Bukkit.getScheduler().cancelTask(particleTask);
                event.getEntity().setGravity(true);
                if (event.getHitEntity() == null) return;
                RegistryEntity entity = RegistryEntity.getRegistryEntity(event.getHitEntity());
                if (entity == null) return;
                entity.entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
                entity.entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 255));
            }
        };
    }

    static int particleTask;
    @Override
    public AbilityAction ability(ShootBowAbility classs) {
        return new AbilityAction("overwhelm_onshoot") {
            @Override
            public void ability(AEvent e, RegistryPlayer player) {
                ShootBowEvent event = (ShootBowEvent) e;
                RegistryItem item = RegistryItem.getItemByItemStack(event.getBow());
                if (item == null) return;
                Entity projectile = event.getProjectile();
                projectile.setGravity(false);
                projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_WARDEN_DEATH, 1, 0);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> {
                    if (!projectile.isDead()) projectile.remove();
                }, 400L);
                particleTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Minedom.getPlugin(), () -> {
                    if (projectile.isDead()) Bukkit.getScheduler().cancelTask(particleTask);
                    Location location = projectile.getLocation();
                    location.getWorld().spawnParticle(Particle.SCULK_SOUL, location, 5, 0.3, 0.3, 0.3,0);
                    Particle.DustTransition dustTransition = new Particle.DustTransition(Color.PURPLE, Color.BLACK, 10F);
                    location.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, location, 20, 0.5, 0.5, 0.5, 0, dustTransition);
                },0L, 1L);
            }
        };
    }
}
