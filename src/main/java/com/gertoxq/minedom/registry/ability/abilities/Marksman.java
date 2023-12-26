package com.gertoxq.minedom.registry.ability.abilities;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.events.Custom.Events.RegistryHit.RegistryHitEvent;
import com.gertoxq.minedom.registry.ability.ItemAbility;
import com.gertoxq.minedom.registry.ability.action.AbilityAction;
import com.gertoxq.minedom.registry.ability.TriggerFace.HitAbility;
import com.gertoxq.minedom.registry.RegistryPlayer;
import org.bukkit.ChatColor;

import java.util.ArrayList;

public class Marksman extends ItemAbility implements HitAbility {

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getId() {
        return "marksman";
    }

    @Override
    public AbilityState getState() {
        return AbilityState.PASSIVE;
    }


    @Override
    public int getCooldown() {
        return 0;
    }

    @Override
    public TriggerType getTriggerType() {
        return TriggerType.MAINHAND;
    }

    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+"Your melee hits deal"+ ChatColor.DARK_RED +" 80%"+ChatColor.GRAY+ " less damage");
        return lore;
    }

    @Override
    public AbilityAction ability(HitAbility classs) {
        return new AbilityAction(getId()) {
            @Override
            public void ability(AEvent e, RegistryPlayer player) {
                RegistryHitEvent event = (RegistryHitEvent) e;
                if (event.getSource() == RegistryHitEvent.DamageSource.MELEE) event.setDamage(event.getDamage()*0.2);
            }
        };
    }
}
