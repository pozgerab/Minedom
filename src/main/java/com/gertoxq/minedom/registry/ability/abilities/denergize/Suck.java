package com.gertoxq.minedom.registry.ability.abilities.denergize;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryDeath.KillEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryDeath.RegistryDeathEvent;
import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.ability.interfaces.actionfaces.ManaConsumerAction;
import com.gertoxq.minedom.registry.ability.interfaces.actionfaces.StatBuffAction;
import com.gertoxq.minedom.registry.entity.Entities.DamageIndicator;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.util.ScheduleCleanup;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Vibration;

public class Suck extends AbilityAction implements StatBuffAction, ManaConsumerAction {
    public Suck() {
        super("denergize.suck");
    }

    @Override
    public void ability(AEvent e, RegistryPlayer player) {
        KillEvent event = (KillEvent) e;

        if (!consume(player)) return;

        Location playerLoc = player.entity.getLocation();
        Location deathLoc = event.getEntity().entity.getLocation().add(0,1,0);
        RegistryEntity entity = new DamageIndicator();
        entity.spawn(playerLoc);
        entity.entity.setCustomNameVisible(false);

        applyBuff(player, Stat.REGENERGY, 100, 100);
        applyBuff(player, Stat.VITALIS, 100, 100);
        applyBuff(player, Stat.BLESSING, 100, 100);
        applyBuff(player, Stat.MANA_REGEN, 100, 100);

        new ScheduleCleanup(() -> {
            entity.entity.teleport(player.player.getLocation().add(0,0.3,0));
            Vibration effect = new Vibration(new Vibration.Destination.EntityDestination(entity.entity), 10);
            playerLoc.getWorld().spawnParticle(Particle.VIBRATION, deathLoc, 30, 0.1,0.01,0.1, effect);
        }, 100, 0, entity::remove);
    }

    @Override
    public double getManaCost() {
        return 50;
    }
}
