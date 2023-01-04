package com.gertoxq.minedom.registry.ability.abilities;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.events.Events.MagicHitEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.item.RegistryItem;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Overwhelm extends Ability {
    public Overwhelm() {
        super(ProjectileHitEvent.class);
    }

    @Override
    public String setName() {
        return "Overwhelm";
    }

    @Override
    public Double setBaseDamage() {
        return 0.0;
    }

    @Override
    public Abilitystate setState() {
        return Abilitystate.ACTIVE;
    }

    @Override
    public int setCooldown() {
        return 0;
    }

    @Override
    public ArrayList<String> setLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+"Shoot in a straight line.");
        lore.add(ChatColor.GRAY+"On hitting en enemy, blind and froze them for 3s.");
        lore.add(ChatColor.DARK_GRAY +"If enemy is closer than 20 blocks,");
        lore.add(ChatColor.DARK_GRAY+"reduce final damage by"+ ChatColor.RED +" 80%");
        return lore;
    }

    @Override
    public Boolean setHasRequirement() {
        return false;
    }

    @Override
    public Skill setRequirementType() {
        return null;
    }

    @Override
    public int setRequirementLevel() {
        return 0;
    }

    @Override
    public void ability(EntityDamageByEntityEvent e, RegistryPlayer player) {
        RegistryEntity target = RegistryEntity.getRegistryEntity(e.getEntity());
        if (target == null) return;
        if (target.entity.getLocation().distance(player.player.getLocation()) < 20) {
            e.setDamage(e.getDamage()*0.2);
        }
    }

    @Override
    public void ability(MagicHitEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(EntityDeathEvent e, RegistryPlayer player) {

    }


    static int particleTask;
    @Override
    public void ability(EntityShootBowEvent e, RegistryPlayer player) {
        RegistryItem item = RegistryItem.getItemByItemStack(e.getBow());
        if (item == null) return;
        Entity projectile = e.getProjectile();
        projectile.setGravity(false);
        projectile.getWorld().playSound(projectile.getLocation(), Sound.ENTITY_WARDEN_DEATH, 1, 0);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> {
            if (!projectile.isDead()) projectile.remove();
        }, 400L);
        particleTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(Minedom.getPlugin(), () -> {
            if (projectile == null || projectile.isDead()) Bukkit.getScheduler().cancelTask(particleTask);
            Location location = projectile.getLocation();
            location.getWorld().spawnParticle(Particle.SCULK_SOUL, location, 5, 0.3, 0.3, 0.3,0);
            Particle.DustTransition dustTransition = new Particle.DustTransition(Color.PURPLE, Color.BLACK, 10F);
            location.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, location, 20, 0.5, 0.5, 0.5, 0, dustTransition);
        },0L, 1L);
    }

    @Override
    public void ability(ProjectileHitEvent e, RegistryPlayer player) {
        Bukkit.getScheduler().cancelTask(particleTask);
        e.getEntity().setGravity(true);
        if (e.getHitEntity() == null) return;
        RegistryEntity entity = RegistryEntity.getRegistryEntity(e.getHitEntity());
        if (entity == null) return;
        entity.entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
        entity.entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 255));
    }
}
