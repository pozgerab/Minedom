package com.gertoxq.minedom.registry.ability.abilities;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.RegistryHitEvent;
import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.registry.ability.ItemAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.HitAbility;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.util.Glow;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Hawkeye extends ItemAbility implements HitAbility {

    @Override
    public String getName() {
        return "Hawkeye";
    }

    @Override
    public String getId() {
        return "hawkeye";
    }

    @Override
    public AbilityState getState() {
        return AbilityState.PASSIVE;
    }

    @Override
    public int getCooldown() {
        return 0;
    }

    @Override
    public TriggerType getTriggerType() {
        return TriggerType.ARMORSLOT;
    }

    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Reveals near enemies on hit.");
        return lore;
    }

    @Override
    public AbilityAction ability(HitAbility classs) {
        return new AbilityAction(10, getId()) {
            @Override
            public void ability(AEvent e, RegistryPlayer player) {
                RegistryHitEvent event = (RegistryHitEvent) e;
                List<Entity> entities = event.getEntity().entity.getNearbyEntities(10, 10, 10);
                entities.add(event.getEntity().entity);
                RegistryEntity.filterRegisteredEntities(entities).forEach(entity -> {
                    if (entity instanceof RegistryPlayer) return;
                    Glow glow = new Glow(player.player);
                    glow.setEntityGlowDuration(entity.entity, ChatColor.BLUE, 200);
                });
            }
    };
}}
