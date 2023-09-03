package com.gertoxq.minedom.registry.item;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

/**
 * Clickable item
 */
public interface ClickableItem {

    /**
     * Actions to run when a player interacts
     * @param e Interact event
     */
    void onInteract(PlayerInteractEvent e);

    /**
     * Determines when to run the {@link #onInteract(PlayerInteractEvent)} by click type
     * @return Custom click type
     */
    ClickType trigger();

    /**
     * Whether to cancel the default click event
     * @return boolean
     */
    boolean cancelDefault();

    /**
     * Checks the click type and executes {@link #onInteract(PlayerInteractEvent)}
     * @param item Item interacted with
     * @param e Interact event
     */
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

    /**
     * Click types
     */
    enum ClickType {
        RIGHT_CLICK,
        RIGHT_CLICK_AIR,
        RIGHT_CLICK_BLOCK,
        LEFT_CLICK,
        LEFT_CLICK_AIR,
        LEFT_CLICK_BLOCK
    }
}
