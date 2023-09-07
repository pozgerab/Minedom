package com.gertoxq.minedom.registry.entity;

import com.gertoxq.minedom.registry.entity.Entities.*;

/**
 * Registers custom entity types
 */
public class RegisterEntities {

    /**
     * Registers custom entities (Insert yours here and run {@link RegistryEntity#register()} on them)
     */
    public RegisterEntities() {
        new Sheep().register();
        new IronGolem().register();
        new DamageIndicator().register();
        new SuperGolem().register();
        new Zombie().register();
        new VanillaEntity().register();
    }

}
