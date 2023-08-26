package com.gertoxq.minedom.registry.ability.abilities;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.MeleeHitEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.TriggerFace.MeleeHitAbility;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import com.gertoxq.minedom.string.StrGen;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Lightning extends Ability implements MeleeHitAbility {
    @Override
    public String setName() {
        return "Lightning";
    }

    @Override
    public String setId() {
        return "lightning";
    }

    @Override
    public double setBaseDamage() {
        return 20.0;
    }

    @Override
    public AbilityState setState() {
        return AbilityState.ACTIVE;
    }

    @Override
    public int setCooldown() {
        return 5;
    }

    @Override
    public TriggerType setTriggerType() {
        return TriggerType.MAINHAND;
    }

    @Override
    public ArrayList<String> setLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(StrGen.loreString("Strike nearby enemies with a lightning bolt"));
        lore.add(StrGen.loreString("dealing " + setBaseDamage() + " damage."));
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
    public AbilityAction ability(MeleeHitEvent e, RegistryPlayer player) {
        return new AbilityAction(5) {
            @Override
            public void ability(AEvent e, RegistryPlayer player) {
                MeleeHitEvent event = (MeleeHitEvent) e;
                RegistryEntity target = event.getEntity();
                List<Entity> entities = target.entity.getNearbyEntities(4,4,4);
                for (RegistryEntity entity: RegistryEntity.filterRegisteredEntities(entities)) {
                    if (entity instanceof RegistryPlayer) continue;
                    entity.magicdamage(setBaseDamage(), player);
                    entity.entity.getLocation().getWorld().strikeLightning(entity.entity.getLocation());
                }
                target.entity.getLocation().getWorld().strikeLightning(target.entity.getLocation());
            }
        };
    }
}
