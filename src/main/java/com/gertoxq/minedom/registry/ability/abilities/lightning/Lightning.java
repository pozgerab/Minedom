package com.gertoxq.minedom.registry.ability.abilities.lightning;

import com.gertoxq.minedom.registry.ability.ItemAbility;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.ability.TriggerFace.MeleeHitAbility;
import com.gertoxq.minedom.registry.ability.interfaces.abilfaces.VisualDamageAbil;
import com.gertoxq.minedom.string.StrGen;

import java.util.ArrayList;

public class Lightning extends ItemAbility implements MeleeHitAbility, VisualDamageAbil {
    @Override
    public String getName() {
        return "Lightning";
    }

    @Override
    public String getId() {
        return "lightning";
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
        lore.add(StrGen.loreString("dealing " + getDamage() + " damage."));
        return lore;
    }

    @Override
    public AbilityAction ability(MeleeHitAbility classs) {
        return new Strike();
    }

    @Override
    public double getDamage() {
        return 20;
    }
}
