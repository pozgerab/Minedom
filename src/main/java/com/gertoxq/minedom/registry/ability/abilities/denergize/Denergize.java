package com.gertoxq.minedom.registry.ability.abilities.denergize;

import com.gertoxq.minedom.registry.ability.ItemAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.DeathAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.KillAbility;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import org.bukkit.ChatColor;

import java.util.ArrayList;

public class Denergize extends ItemAbility implements KillAbility {
    @Override
    public int getCooldown() {
        return 0;
    }

    @Override
    public AbilityAction ability(KillAbility classs) {
        return new Suck();
    }

    @Override
    public String getName() {
        return "Denergize";
    }

    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY +"Upon killing an enemy, you absorb the");
        lore.add(ChatColor.GRAY +"entity's energy and double all amounts of healing");
        return lore;
    }

    @Override
    public AbilityState getState() {
        return AbilityState.ACTIVE;
    }

    @Override
    public String getId() {
        return "denergize";
    }

    @Override
    public TriggerType getTriggerType() {
        return TriggerType.MAINHAND;
    }
}
