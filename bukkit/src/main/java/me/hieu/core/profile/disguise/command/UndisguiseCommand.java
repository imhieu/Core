package me.hieu.core.profile.disguise.command;

import me.hieu.core.Core;
import me.hieu.core.profile.Profile;
import me.hieu.core.profile.disguise.menu.DisguisePlayerMenu;
import me.hieu.libraries.drink.annotation.Command;
import me.hieu.libraries.drink.annotation.Require;
import me.hieu.libraries.drink.annotation.Sender;
import me.hieu.libraries.util.CC;
import org.bukkit.entity.Player;

/**
 * Author: Le Thanh Hieu
 * Date: 17/10/2024
 */

public class UndisguiseCommand {

    @Command(name = "", desc = "undisguise")
    @Require("*")
    public void command(@Sender Player player){
        Profile profile = Core.getInstance().getProfileHandler().getProfileByUniqueId(player.getUniqueId());
        if (profile.isDisguised()){
            profile.setDisguiseProfile(null);
            profile.save();
            profile.updateDisguise();
            return;
        }
        player.sendMessage(CC.translate("&cYou're not disguised!"));
    }

}
