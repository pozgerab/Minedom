package com.gertoxq.minedom.events.Custom.Events.ProjectileHit;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.registry.ability.TriggerFace.ProjectileHitAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.HandlerList;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProjectileHitEvent extends org.bukkit.event.entity.ProjectileHitEvent implements AEvent {

    private RegistryEntity target;
    private RegistryEntity shooter;
    public ProjectileHitEvent(@NotNull Projectile projectile) {
        super(projectile);
    }

    public ProjectileHitEvent(@NotNull Projectile projectile, @Nullable RegistryEntity hitEntity) {
        super(projectile, hitEntity != null ? hitEntity.entity : null);
        this.shooter = RegistryEntity.getRegistryEntity((Entity) projectile.getShooter());
        this.target = hitEntity;
    }

    public RegistryEntity getTarget() {
        return target;
    }
    public void setShooter(RegistryEntity shooter) {
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
    public @NotNull Class<? extends AbilityInterface> getTriggerFace() {
        return ProjectileHitAbility.class;
    }
}
