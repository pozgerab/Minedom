package com.gertoxq.minedom.registry.ability.abilities;

import com.gertoxq.minedom.Minedom;
import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHitEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.ability.TriggerFace.HitAbility;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

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
    public AbilityAction ability(HitAbility classs) {
        return new AbilityAction(10, id) {
            @Override
            public void ability(AEvent e, RegistryPlayer player) {
                RegistryHitEvent event = (RegistryHitEvent) e;
                List<Entity> entities = event.getEntity().entity.getNearbyEntities(10, 10, 10);
                entities.add(event.getEntity().entity);
                RegistryEntity.filterRegisteredEntities(entities).forEach(entity -> {
                    if (entity instanceof RegistryPlayer) return;
                    entity.entity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 1, true, false, false ));
                });
            }
        };
    }
}
