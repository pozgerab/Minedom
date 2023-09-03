package com.gertoxq.minedom.registry.entity;

import com.gertoxq.minedom.registry.entity.Entities.*;

/**
 * Registers custom entities
 */
public class RegisterEntities {

    /**
     * Registers custom entities (Insert yours here)
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
