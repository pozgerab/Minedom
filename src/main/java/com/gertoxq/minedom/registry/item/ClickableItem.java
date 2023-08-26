package com.gertoxq.minedom.registry.item;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

public interface ClickableItem {

    void onInteract(PlayerInteractEvent e);

    ClickType trigger();

    boolean cancelDefault();

    static void emit(ClickableItem item, PlayerInteractEvent e) {
        Action[] actions = switch (item.trigger()) {
            case LEFT_CLICK -> new Action[] {Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK};
            case LEFT_CLICK_BLOCK -> new Action[] {Action.LEFT_CLICK_BLOCK};
            case LEFT_CLICK_AIR -> new Action[] {Action.LEFT_CLICK_AIR};
            case RIGHT_CLICK -> new Action[] {Action.RIGHT_CLICK_AIR,Action.RIGHT_CLICK_BLOCK};
            case RIGHT_CLICK_AIR -> new Action[] {Action.RIGHT_CLICK_AIR};
            case RIGHT_CLICK_BLOCK -> new Action[] {Action.RIGHT_CLICK_BLOCK};
        };
        if (Arrays.stream(actions).toList().contains(e.getAction())) item.onInteract(e);
        e.setCancelled(item.cancelDefault());
    }

    enum ClickType {
        RIGHT_CLICK,
        RIGHT_CLICK_AIR,
        RIGHT_CLICK_BLOCK,
        LEFT_CLICK,
        LEFT_CLICK_AIR,
        LEFT_CLICK_BLOCK
    }
}
