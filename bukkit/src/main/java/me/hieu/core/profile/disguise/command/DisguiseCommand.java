package me.hieu.core.profile.disguise.command;

import me.hieu.core.profile.disguise.menu.DisguisePlayerMenu;
import me.hieu.libraries.drink.annotation.Command;
import me.hieu.libraries.drink.annotation.OptArg;
import me.hieu.libraries.drink.annotation.Require;
import me.hieu.libraries.drink.annotation.Sender;
import me.hieu.libraries.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Author: Le Thanh Hieu
 * Date: 16/10/2024
 */

public class DisguiseCommand {

    @Command(name = "", desc = "disguise")
    @Require("*")
    public void command(@Sender Player player){
        new DisguisePlayerMenu().openMenu(player);
    }

}
