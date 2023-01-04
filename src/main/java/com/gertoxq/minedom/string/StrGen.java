package com.gertoxq.minedom.string;

import org.bukkit.ChatColor;

public class StrGen {

    public static String coloredString(String string, ChatColor color) {
        return color+string+ChatColor.GRAY;
    }

    public static String coloredString(String string, ChatColor color, ChatColor after) {
        return color+string+after;
    }

    public static String displayNameString(String string) {
        return ChatColor.RESET+string;
    }

    public static String loreString(String string) {
        return ChatColor.GRAY+string;
    }

    public static String abilityName(String abilityName) {
        return ChatColor.GOLD+"Ability: "+ChatColor.RED + abilityName;
    }
    public static String abilityName(String abilityName, int cooldown) {
        if (cooldown == 0) {
            return abilityName(abilityName);
        }
        return ChatColor.GOLD + "Ability: " + ChatColor.RED + abilityName + ChatColor.DARK_GRAY + " (" + cooldown + "s cooldown)";
    }

}
