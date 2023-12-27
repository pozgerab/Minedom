package com.gertoxq.minedom.events.Custom.Events.ShootBow;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.registry.ability.TriggerFace.ShootBowAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
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
    /**
     * Handler list
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * @return The handler list
     */
    @Override
    public @NonNull HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Bukkit event requirement
     * @return The handler list
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    @NotNull
    public Class<? extends AbilityInterface> getTriggerFace() {
        return ShootBowAbility.class;
    }
}
