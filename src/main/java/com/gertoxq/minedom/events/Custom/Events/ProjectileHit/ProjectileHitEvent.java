package com.gertoxq.minedom.events.Custom.Events.ProjectileHit;

import com.gertoxq.minedom.events.Custom.AEvent;
import com.gertoxq.minedom.registry.ability.TriggerFace.ProjectileHitAbility;
import com.gertoxq.minedom.registry.ability.TriggerFace.AbilityInterface;
import com.gertoxq.minedom.registry.entity.RegistryEntity;
import org.bukkit.entity.Projectile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProjectileHitEvent extends org.bukkit.event.entity.ProjectileHitEvent implements AEvent {

    private RegistryEntity target;
    public ProjectileHitEvent(@NotNull Projectile projectile) {
        super(projectile);
    }

    public ProjectileHitEvent(@NotNull Projectile projectile, @Nullable RegistryEntity hitEntity) {
        super(projectile, hitEntity != null ? hitEntity.entity : null);
        this.target = hitEntity;
    }

    public RegistryEntity getTarget() {
        return target;
    }

    @Override
    public @NotNull Class<? extends AbilityInterface> getTriggerFace() {
        return ProjectileHitAbility.class;
    }
}
