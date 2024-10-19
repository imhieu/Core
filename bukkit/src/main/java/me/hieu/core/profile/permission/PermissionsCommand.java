package me.hieu.core.profile.permission;

import me.hieu.core.Core;
import me.hieu.core.profile.Profile;
import me.hieu.core.profile.permission.packet.ProfileUpdatePermissionsPacket;
import me.hieu.libraries.drink.annotation.Command;
import me.hieu.libraries.drink.annotation.Require;
import me.hieu.libraries.drink.annotation.Sender;
import me.hieu.libraries.util.CC;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Author: Le Thanh Hieu
 * Date: 19/10/2024
 */

public class PermissionsCommand {

    @Command(name = "", desc = "view profile permissions", usage = "<profile>")
    @Require("*")
    public void command(@Sender CommandSender sender, Profile target){
        List<String> permissions = target.getPermissions();
        if (permissions.isEmpty()){
            sender.sendMessage(CC.translate(target.getFormattedName() + " &chas no individual permissions."));
            return;
        }
        sender.sendMessage(CC.translate(target.getFormattedName() + "'s &6Permissions: &f" + StringUtils.join(permissions, ", ")));
    }

}
