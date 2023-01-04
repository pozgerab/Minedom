package com.gertoxq.minedom.registry.entity;

import com.gertoxq.minedom.registry.entity.Entities.Zombie;

import java.util.Arrays;
import java.util.List;

public enum EntityCategory {
    UNDEAD(new RegistryEntity[]{
            new Zombie()
    });
    final List<RegistryEntity> types;
    EntityCategory(RegistryEntity[] entity) {
        this.types = Arrays.stream(entity).toList();
    }

}
