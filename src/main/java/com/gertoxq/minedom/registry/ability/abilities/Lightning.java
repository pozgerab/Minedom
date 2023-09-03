package com.gertoxq.minedom.registry.ability.abilities;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.MeleeHitEvent;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.ability.TriggerFace.MeleeHitAbility;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import com.gertoxq.minedom.string.StrGen;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Lightning extends Ability implements MeleeHitAbility {
    @Override
    public String getName() {
        return "Lightning";
    }

    @Override
    public String getId() {
        return "lightning";
    }

    @Override
    public double getBaseDamage() {
        return 20.0;
    }

    @Override
    public AbilityState getState() {
        return AbilityState.ACTIVE;
    }

    @Override
    public int getCooldown() {
        return 5;
    }

    @Override
    public TriggerType getTriggerType() {
        return TriggerType.MAINHAND;
    }

    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(StrGen.loreString("Strike nearby enemies with a lightning bolt"));
        lore.add(StrGen.loreString("dealing " + getBaseDamage() + " damage."));
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
    public AbilityAction ability(MeleeHitAbility classs) {
        return new AbilityAction(5, id) {
            @Override
            public void ability(AEvent e, RegistryPlayer player) {
                MeleeHitEvent event = (MeleeHitEvent) e;
                RegistryEntity target = event.getEntity();
                List<Entity> entities = target.entity.getNearbyEntities(4,4,4);
                for (RegistryEntity entity: RegistryEntity.filterRegisteredEntities(entities)) {
                    if (entity instanceof RegistryPlayer) continue;
                    entity.magicdamage(getBaseDamage(), player);
                    entity.entity.getLocation().getWorld().strikeLightning(entity.entity.getLocation());
                }
                target.entity.getLocation().getWorld().strikeLightning(target.entity.getLocation());
            }
        };
    }
}
