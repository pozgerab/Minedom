package com.gertoxq.minedom.registry.entity;

import com.gertoxq.minedom.registry.entity.Entities.Zombie;

import java.util.Arrays;
import java.util.List;

/**
 * Custom entity categories
 */
public enum EntityCategory {
    UNDEAD(new RegistryEntity[] {
            new Zombie()
    });
    /**
     * Entity instances that is in the category
     */
    final List<RegistryEntity> types;

    /**
     * Init
     * @param entities Custom entity instances that are in the category
     */
    EntityCategory(RegistryEntity[] entities) {
        this.types = Arrays.stream(entities).toList();
    }

}
