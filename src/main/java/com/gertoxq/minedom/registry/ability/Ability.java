package com.gertoxq.minedom.registry.ability;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.*;
import com.gertoxq.minedom.registry.ability.TriggerFace.*;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import com.gertoxq.minedom.string.StrGen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class Ability implements AbilityInterface {

    public static ArrayList<Ability> defaultAbilities = new ArrayList<>();
    public String name;
    public int cooldown;
    protected HashMap<UUID, Long> cooldownMap = new HashMap<>();
    protected HashMap<AbilityAction, Long> cooldowns = new HashMap<>();
    @NotNull
    public final Boolean hasRequirement;
    @NotNull
    public final String id;
    @Nullable
    public final Skill requirementType;
    public final int requirementLevel;
    @Nullable
    public final ArrayList<String> lore;
    @NotNull
    public final Double baseDamage;
    @NotNull
    public final AbilityState state;
    @NotNull
    public final TriggerType triggerType;

    public Ability() {
        this.id = setId();
        this.name = setName();
        this.state = setState();
        this.triggerType = setTriggerType();
        this.cooldown = setCooldown();
        this.lore = new ArrayList<>();
        if (state == AbilityState.PASSIVE) {
            lore.addAll(setLore());
        } else if (state == AbilityState.ACTIVE) {
            lore.add(StrGen.abilityName(setName(), setCooldown()));
            if (setLore() != null) lore.addAll(setLore());
        } else if (state == AbilityState.FULL_ARMOR) {
            lore.add(StrGen.fullsetAbilityName(setName(), setCooldown()));
            if (setLore() != null) lore.addAll(setLore());
        } else {
            lore.add("");
        }
        this.hasRequirement = setHasRequirement();
        this.requirementType = setRequirementType();
        this.requirementLevel = setRequirementLevel();
        this.baseDamage = setBaseDamage();
        if (!hasRequirement) defaultAbilities.add(this);
    }

    public abstract String setName();

    public abstract String setId();

    public abstract double setBaseDamage();

    public abstract AbilityState setState();

    /**
     * Specify the displayed initCooldown. JUST VISUAL
     * @return Cooldown in sec
     */
    public abstract int setCooldown();

    public abstract TriggerType setTriggerType();

    public abstract ArrayList<String> setLore();

    public abstract boolean setHasRequirement();

    public abstract Skill setRequirementType();

    public abstract int setRequirementLevel();

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
    private void handleAbility(AEvent e, RegistryPlayer player) {
        AbilityAction action = sortAction(e);
        if (setHasRequirement()) {
            if (player.skillLevels.get(setRequirementType()) < setRequirementLevel()) return;
        }
        if (action.initCooldown() == 0) {
            action.ability(e, player);
            return;
        }
        if (cooldownMap.containsKey(player.player.getUniqueId())) {
            long timeElapsed = System.currentTimeMillis() - cooldownMap.get(player.player.getUniqueId());
            if (action.cooldown() != 0) {
                if (timeElapsed >= action.cooldown() * 1000L) {
                    cooldownMap.put(player.player.getUniqueId(), System.currentTimeMillis());
                    action.ability(e, player);
                    return;
                }
            }
        }
        action.ability(e, player);
        cooldownMap.put(player.player.getUniqueId(), System.currentTimeMillis());
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
