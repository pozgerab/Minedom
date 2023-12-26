package com.gertoxq.minedom.registry.enchant;

import com.gertoxq.minedom.registry.ability.Ability;

import java.util.ArrayList;
import java.util.List;

public abstract class Enchant extends Ability {
    @Override
    public List<String> buildMultipleDesc(List<Ability> abilities) {
        List<String> builder = new ArrayList<>();
        int i = 0;
        StringBuilder line = new StringBuilder();
        while (i < abilities.size()) {
            line.append(abilities.get(i++).getName());
            if (i % 3 == 0) {
                builder.add(line.toString());
                line = new StringBuilder();
            }
        }
        return builder;
    }
}
