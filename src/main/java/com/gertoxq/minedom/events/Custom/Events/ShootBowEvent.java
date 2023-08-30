package com.gertoxq.minedom.events.Custom.Events;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.registry.ability.TriggerFace.ShootBowAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShootBowEvent extends EntityShootBowEvent implements AEvent {

    private final RegistryEntity shooter;

    public ShootBowEvent(@NotNull RegistryEntity shooter, @Nullable ItemStack bow, @Nullable ItemStack consumable, @NotNull Entity projectile, @NotNull EquipmentSlot hand, float force, boolean consumeItem) {
        super(shooter.entity, bow, consumable, projectile, hand, force, consumeItem);
        this.shooter = shooter;
    }

    public RegistryEntity getShooter() {
        return shooter;
    }

    @Override
    @NotNull
    public Class<? extends AbilityInterface> getTriggerFace() {
        return ShootBowAbility.class;
    }
}
