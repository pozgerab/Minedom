package com.gertoxq.minedom.registry.ability;

import com.gertoxq.minedom.events.Events.MagicHitEvent;
import com.gertoxq.minedom.events.Events.RegistryDeathEvent;
import com.gertoxq.minedom.registry.ability.abilities.Devour;
import com.gertoxq.minedom.registry.player.RegistryPlayer;
import com.gertoxq.minedom.skill.Skill;
import com.gertoxq.minedom.string.StrGen;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.*;

public abstract class Ability {

    public static ArrayList<Ability> defaultAbilities = new ArrayList<>();
    public List<Class<? extends Event>> event;
    public String name;
    public int cooldown;
    protected HashMap<UUID, Long> cooldownMap = new HashMap<>();
    public Boolean hasRequirement;
    public Skill requirementType;
    public int requirementLevel;
    public ArrayList<String> lore;
    public Double baseDamage;
    public Abilitystate state;

    @SafeVarargs
    public Ability(Class<? extends Event>... event /* <- Get the class of an event*/) {
        this.event = Arrays.stream(event).toList();
        this.name = setName();
        this.state = setState();
        this.cooldown = setCooldown();
        this.lore = new ArrayList<>();
        if (state == Abilitystate.PASSIVE) {
            lore.addAll(setLore());
        } else if (state == Abilitystate.ACTIVE) {
            lore.add(StrGen.abilityName(setName(), setCooldown()));
            lore.addAll(setLore());
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

    public abstract Double setBaseDamage();

    public abstract Abilitystate setState();

    public abstract int setCooldown();

    public abstract ArrayList<String> setLore();

    public abstract Boolean setHasRequirement();

    public abstract Skill setRequirementType();

    public abstract int setRequirementLevel();

    public abstract void ability(EntityDamageByEntityEvent e, RegistryPlayer player);

    public abstract void ability(RegistryDeathEvent e, RegistryPlayer player);

    public abstract void ability(MagicHitEvent e, RegistryPlayer player);

    public abstract void ability(EntityShootBowEvent e, RegistryPlayer player);

    public abstract void ability(ProjectileHitEvent e, RegistryPlayer player);

    public void handleEvent(Event e, RegistryPlayer player) {
        handleAbility(e, player);
    }

    protected void handleAbility(Event e, RegistryPlayer player) {

        if (setHasRequirement()) {
            if (player.skillLevels.get(setRequirementType()) < setRequirementLevel()) return;
        }

        if (cooldownMap.containsKey(player.player.getUniqueId())) {
            long timeElapsed = System.currentTimeMillis() - cooldownMap.get(player.player.getUniqueId());
            if (timeElapsed >= cooldown * 1000L) {
                cooldownMap.put(player.player.getUniqueId(), System.currentTimeMillis());
                if (e instanceof EntityDamageByEntityEvent event) ability(event, player);
                else if (e instanceof RegistryDeathEvent event) ability(event, player);
                else if (e instanceof MagicHitEvent event) ability(event, player);
                else if (e instanceof EntityShootBowEvent event) ability(event, player);
                else if (e instanceof ProjectileHitEvent event) ability(event, player);

            }

            return;
        }
        if (e instanceof EntityDamageByEntityEvent event) ability(event, player);
        else if (e instanceof RegistryDeathEvent event) ability(event, player);
        else if (e instanceof MagicHitEvent event) ability(event, player);
        else if (e instanceof EntityShootBowEvent event) ability(event, player);
        else if (e instanceof ProjectileHitEvent event) ability(event, player);
        cooldownMap.put(player.player.getUniqueId(), System.currentTimeMillis());
    }

    public enum Abilitystate {
        PASSIVE,
        ACTIVE
    }

    /*enum TriggerEvent {
        ON_MELEE_KILL("Melee kill"),
        ON_MELEE_HIT("Melee hit"),
        ON_ARROW_KILL("Bow kill"),
        ON_ARROW_HIT("Bow hit"),
        ON_INCOMING_HIT("Incoming hit"),
        ON_INCOMING_DAMAGE("Incoming damage"),
        ON_BLOCK_BREAK("Block break");

        final String name;
        TriggerEvent(String name) {
            this.name = name;
        }
    }*/

}
