package me.hieu.core.grant.command;

import me.hieu.core.grant.menu.GrantsMenu;
import me.hieu.core.profile.Profile;
import me.hieu.libraries.drink.annotation.Command;
import me.hieu.libraries.drink.annotation.Require;
import me.hieu.libraries.drink.annotation.Sender;
import org.bukkit.entity.Player;

/**
 * Author: Le Thanh Hieu
 * Date: 07/10/2024
 */

public class GrantsCommand {

    @Command(name = "", desc = "player grants", usage = "<player>")
    @Require("*")
    public void command(@Sender Player player, Profile target){
        new GrantsMenu(target).openMenu(player);
    }

}
