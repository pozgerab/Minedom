package com.gertoxq.minedom.registry.ability;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.*;
import com.gertoxq.minedom.registry.ability.TriggerFace.*;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import com.gertoxq.minedom.string.StrGen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.gertoxq.minedom.registry.entity.RegistryEntity;

import java.util.*;

/**
 * Custom abilities. One ability can hold multiple {@link AbilityAction}s by implementing extensions of {@link AbilityInterface}. One item can hold multiple abilities
 */
public abstract class Ability implements AbilityInterface {

    /**
     * Ability display name
     */
    public String name;
    /**
     * Ability display cooldown (just visual, displayed on items)
     */
    public int cooldown;
    /**
     * Stores the cooldowns of actions. Every ability have this separately
     */
    protected HashMap<String, Long> cooldowns = new HashMap<>();
    /**
     * Whether the ability has requirement
     */
    @NotNull
    public final Boolean hasRequirement;
    /**
     * Unique identifier
     */
    @NotNull
    public final String id;
    /**
     * Requirement skill type
     */
    @Nullable
    public final Skill requirementType;
    /**
     * Requirement level
     */
    public final int requirementLevel;
    /**
     * Lore
     */
    @Nullable
    public final ArrayList<String> lore;
    /**
     * Base damage, used in ability damage calculations
     */
    @NotNull
    public final Double baseDamage;
    /**
     * State, represents how an ability is displayed on an item
     */
    @NotNull
    public final AbilityState state;
    /**
     * Trigger type, represents when to trigger the ability
     */
    @NotNull
    public final TriggerType triggerType;

    /**
     * New instance of ability
     */
    public Ability() {
        this.id = getId();
        this.name = getName();
        this.state = getState();
        this.triggerType = getTriggerType();
        this.cooldown = getCooldown();
        this.lore = new ArrayList<>();
        if (state == AbilityState.PASSIVE) {
            lore.addAll(getLore());
        } else if (state == AbilityState.ACTIVE) {
            lore.add(StrGen.abilityName(getName(), getCooldown()));
            if (getLore() != null) lore.addAll(getLore());
        } else if (state == AbilityState.FULL_ARMOR) {
            lore.add(StrGen.fullsetAbilityName(getName(), getCooldown()));
            if (getLore() != null) lore.addAll(getLore());
        } else {
            lore.add("");
        }
        this.hasRequirement = getHasRequirement();
        this.requirementType = getRequirementType();
        this.requirementLevel = getRequirementLevel();
        this.baseDamage = getBaseDamage();
    }
    /**
     * @return The ability display name
     */
    public abstract String getName();
    /**
     * @return The unique id of the ability
     */
    public abstract String getId();

    /**
     * @return The base ability damage, used in {@link RegistryEntity#abilitydamage(Ability, RegistryPlayer)}
     */
    public abstract double getBaseDamage();

    /**
     * @return The ability state
     */
    public abstract AbilityState getState();

    /**
     * Specify the displayed cooldown. JUST VISUAL
     * @return Cooldown in sec
     */
    public abstract int getCooldown();

    /**
     * @return Ability trigger type
     */
    public abstract TriggerType getTriggerType();

    /**
     * @return The lore of the ability
     */
    public abstract ArrayList<String> getLore();

    /**
     * @return Whether the ability has skill requirement
     */
    public abstract boolean getHasRequirement();

    /**
     * @return The requirement skill type
     */
    public abstract Skill getRequirementType();

    /**
     * @return  The required skill level of the required skill type
     */
    public abstract int getRequirementLevel();

    /**
     * Handles an event passed to a player
     * @param e Event
     * @param player Player
     */
    public void handleEvent(AEvent e, RegistryPlayer player) {
        handleAbility(e, player);
    }

    /**
     * Checks ability cooldowns and executes abilities
     * @param e Event
     * @param player Player
     */
    public void handleAbility(AEvent e, RegistryPlayer player) {
        AbilityAction action = sortAction(e);
        if (getHasRequirement()) {
            if (player.skillLevels.get(getRequirementType()) < getRequirementLevel()) return;
        }
        if (action.initCooldown() == 0) {
            action.ability(e, player);
            return;
        }
        if (cooldowns.containsKey(action.getId())) {
            long timeElapsed = System.currentTimeMillis() - cooldowns.get(action.getId());
            if (action.cooldown() != 0) {
                if (timeElapsed >= action.cooldown() * 1000L) {
                    cooldowns.put(action.getId(), System.currentTimeMillis());
                    action.ability(e, player);
                    return;
                }
                return;
            }
        }
        action.ability(e, player);
        cooldowns.put(action.getId(), System.currentTimeMillis());
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

    /**
     * Returns all the ability actions that an ability holds
     * @return List of ability action
     */
    public List<AbilityAction> getActions() {
        List<AbilityAction> actions = new ArrayList<>();
        if (this instanceof DeathAbility ability) actions.add(ability.ability(ability));
        if (this instanceof MagicHitAbility ability) actions.add(ability.ability(ability));
        if (this instanceof ShootBowAbility ability) actions.add(ability.ability(ability));
        if (this instanceof ProjectileHitAbility ability) actions.add(ability.ability(ability));
        if (this instanceof MeleeHitAbility ability) actions.add(ability.ability(ability));
        if (this instanceof HitAbility ability) actions.add(ability.ability(ability));
        if (this instanceof InitAbility ability) actions.add(ability.ability(ability));
        return actions;
    }

    /**
     * Represents how an ability is displayed on an item
     */
    public enum AbilityState {
        PASSIVE,
        ACTIVE,
        FULL_ARMOR
    }

    /**
     * Represents when to trigger an ability
     */
    public enum TriggerType {

        MAINHAND,
        ARMORSLOT,

        FULL_ARMOR

    }
}
