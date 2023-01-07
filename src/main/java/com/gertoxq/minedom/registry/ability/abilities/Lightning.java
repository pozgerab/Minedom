package com.gertoxq.minedom.registry.ability.abilities;

import com.gertoxq.minedom.events.Events.MagicHitEvent;
import com.gertoxq.minedom.events.Events.RegistryDeathEvent;
import com.gertoxq.minedom.math.DmgCalc;
import com.gertoxq.minedom.registry.ability.Ability;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import com.gertoxq.minedom.string.StrGen;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.*;

import java.util.ArrayList;
import java.util.List;

public class Lightning extends Ability {

    public Lightning() {
        super(EntityDamageByEntityEvent.class);
    }

    @Override
    public String setName() {
        return "Lightning";
    }

    @Override
    public Double setBaseDamage() {
        return 20.0;
    }

    @Override
    public Abilitystate setState() {
        return Abilitystate.ACTIVE;
    }

    @Override
    public int setCooldown() {
        return 5;
    }

    @Override
    public ArrayList<String> setLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(StrGen.loreString("Strike nearby enemies with a lightning bolt"));
        lore.add(StrGen.loreString("dealing " + setBaseDamage() + " damage."));
        return lore;
    }

    @Override
    public Boolean setHasRequirement() {
        return false;
    }

    @Override
    public Skill setRequirementType() {
        return null;
    }

    @Override
    public int setRequirementLevel() {
        return 0;
    }

    @Override
    public void ability(EntityDamageByEntityEvent e, RegistryPlayer player) {
        Entity target = e.getEntity();
        List<Entity> entities = target.getNearbyEntities(4,4,4);
        for (Entity entity: RegistryEntity.filterRegisteredEntities(entities)) {
            if (entity == player.entity) continue;
            entity.getLocation().getWorld().strikeLightning(entity.getLocation());
            RegistryEntity registryEntity = RegistryEntity.getRegistryEntity(entity);
            DmgCalc.magicdamageEntity(registryEntity, setBaseDamage(), player);
        }
        target.getLocation().getWorld().strikeLightning(target.getLocation());
    }

    @Override
    public void ability(RegistryDeathEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(MagicHitEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(EntityShootBowEvent e, RegistryPlayer player) {

    }

    @Override
    public void ability(ProjectileHitEvent e, RegistryPlayer player) {

    }
}
