package com.gertoxq.minedom.registry.ability.abilities;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.events.Events.MagicHitEvent;
import com.gertoxq.minedom.events.Events.RegistryDeathEvent;
import com.gertoxq.minedom.events.Events.RegistryHitEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import com.gertoxq.minedom.tools.GlowingEntities;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutBlockBreakAnimation;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEffect;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;

public class Hawkeye extends Ability {

    public Hawkeye() {
        super(RegistryHitEvent.class, EntityDamageByEntityEvent.class);
    }

    @Override
    public String setName() {
        return "Hawkeye";
    }

    @Override
    public Double setBaseDamage() {
        return null;
    }

    @Override
    public Abilitystate setState() {
        return Abilitystate.PASSIVE;
    }

    @Override
    public int setCooldown() {
        return 0;
    }

    @Override
    public ArrayList<String> setLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Reveals near enemies on hit.");
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
    }

    @Override
    public void ability(RegistryDeathEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(MagicHitEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(EntityShootBowEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(ProjectileHitEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(RegistryHitEvent e, RegistryPlayer player) {

        CraftPlayer craftPlayer = (CraftPlayer) player.player;
        EntityPlayer serverPlayer = craftPlayer.getHandle();

        PlayerConnection listener = serverPlayer.b;

        e.getTarget().entity.getNearbyEntities(10, 10, 10).forEach(entity -> {
            if (RegistryEntity.getRegistryEntity(entity) != null && RegistryEntity.getRegistryEntity(entity) != player) {
                try {
                    Minedom.glowingEntities.setGlowing(entity, player.player, ChatColor.AQUA);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> {
                        try {
                            Minedom.glowingEntities.unsetGlowing(entity, player.player);
                        } catch (ReflectiveOperationException ex) {
                            ex.printStackTrace();
                        }
                    }, 60);
                } catch (ReflectiveOperationException ex) {
                    ex.printStackTrace();
                }
            }
            });

    }
}
