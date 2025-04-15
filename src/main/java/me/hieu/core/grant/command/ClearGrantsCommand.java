package me.hieu.core.grant.command;

import me.hieu.core.Core;
import me.hieu.core.grant.packet.GrantsClearPacket;
import me.hieu.core.profile.Profile;
import me.hieu.libraries.drink.annotation.Command;
import me.hieu.libraries.drink.annotation.Require;
import me.hieu.libraries.drink.annotation.Sender;
import me.hieu.libraries.util.CC;
import org.bukkit.command.CommandSender;

/**
 * Author: Le Thanh Hieu
 * Date: 10/10/2024
 */

public class ClearGrantsCommand {

    @Command(name = "", desc = "clear grants", usage = "<player>")
    @Require("core.grants")
    public void command(@Sender CommandSender sender, Profile target){
        sender.sendMessage(CC.translate("&aGrants of &r" + target.getFormattedName() + " &ahas been cleared."));
        GrantsClearPacket packet = new GrantsClearPacket(target.getUniqueId());
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

}
