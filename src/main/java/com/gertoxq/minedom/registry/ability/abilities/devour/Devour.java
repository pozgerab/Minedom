package com.gertoxq.minedom.registry.ability.abilities.devour;

import com.gertoxq.minedom.Stats.Stat;
import com.gertoxq.minedom.registry.ability.ItemAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.*;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import org.bukkit.*;

import java.util.ArrayList;

public class Devour extends ItemAbility implements DeathAbility, InitAbility {

    @Override
    public String getName() {
        return "Devour";
    }

    @Override
    public String getId() {
        return "devour";
    }

    @Override
    public AbilityState getState() {
        return AbilityState.FULL_ARMOR;
    }

    @Override
    public int getCooldown() {
        return 12;
    }

    @Override
    public TriggerType getTriggerType() {
        return TriggerType.FULL_ARMOR;
    }

    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "On killing an enemy, get 20 " + Stat.AGILITY.displayName);
        lore.add(ChatColor.GRAY + "and 10 " + Stat.STRENGTH.displayName + " for 4 seconds.");
        lore.add(ChatColor.GRAY + "On expiring, the lost buffs get");
        lore.add(ChatColor.GRAY + "converted to absorption for 10s.");
        lore.add(ChatColor.DARK_GRAY + "(Buffs and absorption stack)");
        return lore;
    }

    @Override
    public AbilityAction ability(DeathAbility classs) {
        return KillBuff.action;
    }

    @Override
    public AbilityAction ability(InitAbility classs) {
        return new Halo();
    }
}
