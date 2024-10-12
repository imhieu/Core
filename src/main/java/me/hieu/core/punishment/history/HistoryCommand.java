package me.hieu.core.punishment.history;

import me.hieu.core.profile.Profile;
import me.hieu.core.punishment.history.menu.HistoryMenu;
import me.hieu.libraries.drink.annotation.Command;
import me.hieu.libraries.drink.annotation.Require;
import me.hieu.libraries.drink.annotation.Sender;
import org.bukkit.entity.Player;

/**
 * Author: Le Thanh Hieu
 * Date: 11/10/2024
 */

public class HistoryCommand {

    @Command(name = "", desc = "player history", usage = "<player>")
    @Require("*")
    public void command(@Sender Player player, Profile target){
        new HistoryMenu(target).openMenu(player);
    }

}
