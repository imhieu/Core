package me.hieu.core;

import org.bukkit.entity.Player;

/**
 * Author: Le Thanh Hieu
 * Date: 23/10/2024
 */

public class CoreAPI {

    public static String getFormattedName(Player player){
        return Core.getInstance().getProfileHandler().getProfileByUniqueId(player.getUniqueId()).getFormattedName();
    }

}
