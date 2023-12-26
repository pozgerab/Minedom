package com.gertoxq.minedom.registry.ability;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.Init.InitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.MagicHitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.MeleeHitEvent;
import com.gertoxq.minedom.events.Custom.Events.ProjectileHit.ProjectileHitEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryDeath.RegistryDeathEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.RegistryHitEvent;
import com.gertoxq.minedom.events.Custom.Events.ShootBow.ShootBowEvent;
import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.registry.ability.TriggerFace.*;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.ability.interfaces.abilfaces.BasicAbil;
import com.gertoxq.minedom.registry.ability.interfaces.abilfaces.RequirementAbil;

import java.util.List;

public abstract class Ability implements AbilityInterface, BasicAbil {

    private final List<String> desc = buildDesc();

    /**
     * Handles an event passed to a player
     * @param e Event
     * @param player Player
     */
    public void handleEvent(AEvent e, RegistryPlayer player) {
        handleAbility(e, player);
    }

    /**
     * Script to build the lore of the ability
     * @return A description that can be shown on items with this ability
     */
    public abstract List<String> buildDesc();

    /**
     * Gets the description of the ability that can be shown on items holding this ability.
     * @return Description from {@link #buildDesc()}
     */
    public List<String> getDescription() {
        return desc;
    }

    /**
     * Returns a description for multiple abilities that can be shown on items
     * @param abilities Abilities to be shown. This list should contain "this", the instance that it's called on
     * @return A description that can be shown on an item
     */
    public abstract List<String> buildMultipleDesc(List<Ability> abilities);

    /**
     * Checks ability cooldowns and executes abilities
     * @param e Event
     * @param player Player
     */
    public void handleAbility(AEvent e, RegistryPlayer player) {
        AbilityAction action = sortAction(e);
        if (this instanceof RequirementAbil THIS) {
            if (!THIS.condition(player)) return;
        }
        if (action.initCooldown() == 0) {
            action.ability(e, player);
            return;
        }
        if (action.cooldowns.containsKey(action.getId())) {
            long timeElapsed = System.currentTimeMillis() - action.cooldowns.get(action.getId());
            if (action.cooldown() != 0) {
                if (timeElapsed >= action.cooldown() * 1000L) {
                    action.cooldowns.put(action.getId(), System.currentTimeMillis());
                    action.ability(e, player);
                    return;
                }
                return;
            }
        }
        action.ability(e, player);
        action.cooldowns.put(action.getId(), System.currentTimeMillis());
    }

    /**
     * Checks instance of the event and executes ability
     * @param e Event
     */
    public AbilityAction sortAction(AEvent e) {
        if (e instanceof RegistryDeathEvent) return ((DeathAbility) this).ability((DeathAbility) this);
        else if (e instanceof MagicHitEvent) return ((MagicHitAbility) this).ability((MagicHitAbility) this);
        else if (e instanceof ShootBowEvent) return ((ShootBowAbility) this).ability((ShootBowAbility) this);
        else if (e instanceof ProjectileHitEvent) return ((ProjectileHitAbility) this).ability((ProjectileHitAbility) this);
        else if (e instanceof MeleeHitEvent) return  ((MeleeHitAbility) this).ability((MeleeHitAbility) this);
        else if (e instanceof RegistryHitEvent) return  ((HitAbility) this).ability((HitAbility) this);
        else if (e instanceof InitEvent) return ((InitAbility) this).ability((InitAbility) this);
        else throw new RuntimeException("No Event Found");
    }
}


