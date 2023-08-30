package com.gertoxq.minedom.registry.ability.abilities.devour;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.ability.action.Statable;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import org.bukkit.*;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class Halo extends AbilityAction implements Statable {

    final int ROTATE_PER_TICK = 15;
    final double MOVE_PER_TICK = 0.075;
    final static HashMap<RegistryPlayer, Integer> haloMap = new HashMap<>();
    final static HashMap<RegistryPlayer, Vector> upOffset = new HashMap<>();
    final static HashMap<RegistryPlayer, Vector> upRotator = new HashMap<>();

    public Halo() {
        super("halo");
    }

    @Override
    public void cleanUp(RegistryPlayer player) {
        if (haloMap.containsKey(player)) {
            player.player.sendMessage(String.valueOf(Bukkit.getScheduler().isCurrentlyRunning(haloMap.get(player))));
            Bukkit.getScheduler().cancelTask(haloMap.get(player));
            player.player.sendMessage(String.valueOf(Bukkit.getScheduler().isCurrentlyRunning(haloMap.get(player))));
            haloMap.remove(player);
        }
    }

    @Override
    public void ability(AEvent e, RegistryPlayer player) {

        if (!upOffset.containsKey(player)) {
            upOffset.put(player, new Vector(0,0,0));
        }
        if (!upRotator.containsKey(player)) {
            upRotator.put(player, new Vector(0,0,1.2));
        }
        Runnable task = new Runnable() {
            boolean up = true;
            @Override
            public void run() {
                World world = player.player.getWorld();
                Location ustart = player.player.getLocation();
                Location uend = ustart.clone().add(new Vector(0, 2, 0));
                Location umovable = ustart.clone().add(upOffset.get(player));
                if ((umovable.getY() == uend.getY()+MOVE_PER_TICK || umovable.getY() > uend.getY()+MOVE_PER_TICK) || (umovable.getY() == ustart.getY()-MOVE_PER_TICK || umovable.getY() < ustart.getY()-MOVE_PER_TICK)) {
                    up = !up;
                }
                world.spawnParticle(Particle.DRIP_LAVA, umovable.clone().add(upRotator.get(player)), 1, 0.01, 0.01, 0.01, 0/*, new Particle.DustOptions(Color.GREEN, 1.5f)*/);
                //world.spawnParticle(Particle.DRIP_LAVA, umovable.clone().add(upRotator.clone().rotateAroundY(Math.toRadians(180))), 20, 0.01, 0.01, 0.01, 0);
                upRotator.get(player).rotateAroundY(Math.toRadians(ROTATE_PER_TICK));
                upOffset.get(player).add(new Vector(0, up ? MOVE_PER_TICK : -MOVE_PER_TICK, 0));
            }
        };

        int num = Bukkit.getScheduler().scheduleSyncRepeatingTask(Minedom.getPlugin(), task, 0L, 1L);
        haloMap.put(player, num);
    }
}
