package me.hieu.core.profile.disguise.command;

import me.hieu.core.Core;
import me.hieu.core.profile.disguise.menu.DisguisePlayerMenu;
import me.hieu.libraries.drink.annotation.Command;
import me.hieu.libraries.drink.annotation.OptArg;
import me.hieu.libraries.drink.annotation.Require;
import me.hieu.libraries.drink.annotation.Sender;
import me.hieu.libraries.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Author: Le Thanh Hieu
 * Date: 16/10/2024
 */

public class DisguiseCommand {

    @Command(name = "", desc = "disguise")
    @Require("*")
    public void command(@Sender Player player){
        List<String> names = Core.getInstance().getConfigHandler().getDisguiseConfigz().getStringList("names");
        if (names.isEmpty()){
            player.sendMessage(CC.translate("&cDisguise feature hasn't been set up."));
            return;
        }
        new DisguisePlayerMenu().openMenu(player);
    }

}
