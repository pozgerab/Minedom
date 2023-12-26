package com.gertoxq.minedom.events.Custom.Events.RegistryHit;

import com.gertoxq.minedom.math.DmgCalc;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class HitBuffer implements Listener {

    /**
     * Applies the damage reduction based on the defense of the victim
     * @param e {@link MeleeHitEvent}
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onMeleeHit(RegistryHitEvent e) {

        double damage = e.getDamage();
        double reduction = (switch (e.getSource()) {
            case MAGIC -> DmgCalc.getMagicReduction(e.getEntity().stats.getMAGIC_DEFENSE());
            case MELEE -> DmgCalc.getMeleeReduction(e.getEntity().stats.getDEFENSE());
            default -> 1;
        });

        e.setDamage(damage * reduction);
    }
}
