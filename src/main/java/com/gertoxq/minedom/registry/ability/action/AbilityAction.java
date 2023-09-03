package com.gertoxq.minedom.registry.ability.action;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.registry.RegistryPlayer;

/**
 * Class for ability actions. Keep in mind that if you use this as an anonymous class, every time the ability triggers, the class gets recreated,
 * so it can't hold any persistent data and the cooldown cannot be modified mid-action.
 * If you want to avoid this, you need to create a static field with the only once initiated instance and reference that when initiating the ability.
 * {@snippet :
 * public static MyAction action = new MyAction();
 * }
 * Another approach is to create static hashmaps with keys of players and store the persistent value that way.
 */
public abstract class AbilityAction {
    private final int initCooldown;
    private int cooldown = 0;
    private final String id;

    /**
     * Init of ability action with cooldown
     *
     * @param cooldown Cooldown in seconds
     * @param id Unique identifier
     */
    public AbilityAction(int cooldown, String id) {
        this.initCooldown = cooldown;
        this.cooldown = cooldown;
        this.id = id;
    }

    /**
     * @return Unique identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Init of ability action with cooldown of 0
     * @param id Unique identifier
     */
    public AbilityAction(String id) {
        this.id = id;
        this.initCooldown = 0;
    }

    /**
     * @return The initial cooldown of the action
     */
    public int initCooldown() {
        return this.initCooldown;
    }
    /**
     * @return The mid-action cooldown. Use case specified in {@link #setCurrentCooldown(int cooldown)}
     */
    public int cooldown() {
        return this.cooldown;
    }
    /**
     * Sets an executing ability's cooldown. This determinates how much time have to pass before the action can be executed again. Used to reset and modifiy cooldown mid-action. Make sure to reset this to the initial cooldown using {@link #resetCooldown()}.
     * @param cooldown Cooldown in seconds
     */
    public void setCurrentCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    /**
     * Resets the cooldown to the initial value
     */
    public void resetCooldown() {
        this.cooldown = initCooldown;
    }
    /**
     * Executes the ability. MAKE SURE to cast the event to the corresponding event
     * @param e Non cast event
     * @param player Player
     */
    public abstract void ability(AEvent e, RegistryPlayer player);
}
