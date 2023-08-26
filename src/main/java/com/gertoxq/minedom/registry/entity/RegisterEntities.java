package com.gertoxq.minedom.registry.entity;

import com.gertoxq.minedom.registry.entity.Entities.*;

public class RegisterEntities {

    public RegisterEntities() {
        new Sheep().register();
        new IronGolem().register();
        new DamageIndicator().register();
        new SuperGolem().register();
        new Zombie().register();
        new VanillaEntity().register();
    }

}
