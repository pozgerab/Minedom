package com.gertoxq.minedom.registry.ability;

import com.gertoxq.minedom.registry.ability.TriggerFace.*;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.string.StrGen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Custom abilities. One ability can hold multiple {@link AbilityAction}s by implementing extensions of {@link AbilityInterface}. One item can hold multiple abilities
 */
public abstract class ItemAbility extends Ability {

    /**
     * Ability display cooldown (just visual, displayed on items)
     */
    public int cooldown;
    /**
     * Stores the cooldowns of actions. Every ability have this separately
     */
    protected HashMap<String, Long> cooldowns = new HashMap<>();
    /**
     * Lore
     */
    @Nullable
    public final ArrayList<String> lore = new ArrayList<>();
    /**
     * State, represents how an ability is displayed on an item
     */
    @NotNull
    public final AbilityState state;

    @Override
    public List<String> buildMultipleDesc(List<Ability> abilities) {
        List<String> mDescBuilder = new ArrayList<>();
        abilities.forEach(ability -> {
            mDescBuilder.addAll(ability.buildDesc());
            mDescBuilder.add("");
        });
        return mDescBuilder;
    }

    @Override
    public List<String> buildDesc() {
        List<String> descBuiler = new ArrayList<>();
        if (state == AbilityState.PASSIVE) {
            descBuiler.addAll(getLore());
        } else if (state == AbilityState.ACTIVE) {
            descBuiler.add(StrGen.abilityName(getName(), getCooldown()));
            if (getLore() != null) descBuiler.addAll(getLore());
        } else if (state == AbilityState.FULL_ARMOR) {
            descBuiler.add(StrGen.fullsetAbilityName(getName(), getCooldown()));
            if (getLore() != null) descBuiler.addAll(getLore());
        } else {
            descBuiler.add("");
        }
        return descBuiler;
    }

    /**
     * New instance of ability
     */
    public ItemAbility() {
        this.state = getState();
        this.cooldown = getCooldown();
    }

    /**
     * Specify the displayed cooldown. JUST VISUAL
     * @return Cooldown in sec
     */
    public abstract int getCooldown();

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
