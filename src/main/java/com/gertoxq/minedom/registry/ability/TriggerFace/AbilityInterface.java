package com.gertoxq.minedom.registry.ability.TriggerFace;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.registry.player.RegistryPlayer;

/**
 * Superinterface of ability interfaces
 */
public interface AbilityInterface {
    abstract class AbilityAction {
        private int cooldown = 0;

        /**
         * Init of ability action with cooldown
         * @param cooldown Cooldown
         */
        public AbilityAction(int cooldown) {
            this.cooldown = cooldown;
        }

        /**
         * Init of ability action with cooldown of 0
         */
        public AbilityAction() {}

        /**
         * @return The cooldown of the action
         */
        public int cooldown() {
            return this.cooldown;
        }

        /**
         * Executes the ability. MAKE SURE to cast the event to the corresponding event
         * @param e Non cast event
         * @param player Player
         */
        public abstract void ability(AEvent e, RegistryPlayer player);
    }
    abstract class StateAction extends AbilityAction {
        public abstract void cleanUp(RegistryPlayer player);
    }
}
