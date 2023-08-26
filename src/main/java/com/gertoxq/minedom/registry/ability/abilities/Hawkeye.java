package com.gertoxq.minedom.registry.ability.abilities;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHitEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.TriggerFace.HitAbility;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;

public class Hawkeye extends Ability implements HitAbility {

    @Override
    public String setName() {
        return "Hawkeye";
    }

    @Override
    public String setId() {
        return "hawkeye";
    }

    @Override
    public double setBaseDamage() {
        return 0.0;
    }

    @Override
    public AbilityState setState() {
        return AbilityState.PASSIVE;
    }

    @Override
    public int setCooldown() {
        return 0;
    }

    @Override
    public TriggerType setTriggerType() {
        return TriggerType.ARMORSLOT;
    }

    @Override
    public ArrayList<String> setLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Reveals near enemies on hit.");
        return lore;
    }

    @Override
    public boolean setHasRequirement() {
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
    public AbilityAction ability(RegistryHitEvent e, RegistryPlayer player) {
        return new AbilityAction(10) {
            @Override
            public void ability(AEvent e, RegistryPlayer player) {
                RegistryHitEvent event = (RegistryHitEvent) e;
                event.getEntity().entity.getNearbyEntities(10, 10, 10).forEach(entity -> {
                    if (RegistryEntity.getRegistryEntity(entity) != null && RegistryEntity.getRegistryEntity(entity) != player) {
                        entity.setGlowing(true);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Minedom.getPlugin(), () -> {
                            entity.setGlowing(false);
                        }, 60);
                    }
                });
            }
        };
    }
}
