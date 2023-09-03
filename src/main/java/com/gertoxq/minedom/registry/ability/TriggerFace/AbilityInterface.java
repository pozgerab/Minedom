package com.gertoxq.minedom.registry.ability.TriggerFace;

import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.ability.Ability;

/**
 * Superinterface of ability interfaces.
 * Subclasses of this class should look like this
 * {@snippet :
 * public interface MyAbility extends AbilityInterface {
 *      AbilityAction ability(MyAbility classs);
 * }
 * }
 * This returns an {@link AbilityAction}. Please read the doc on {@link AbilityAction} too, it has crucial information.
 * The #ability method's argument is required, so an ability can implement multiple interfaces with this structure.
 * If we want to get the ability action, just pass in the interface itself like the {@link Ability#getActions()} method does.
 */
public interface AbilityInterface {
}
