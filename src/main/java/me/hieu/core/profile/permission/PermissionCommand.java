package me.hieu.core.profile.permission;

import me.hieu.core.Core;
import me.hieu.core.profile.Profile;
import me.hieu.core.profile.permission.packet.ProfileUpdatePermissionsPacket;
import me.hieu.libraries.drink.annotation.Command;
import me.hieu.libraries.drink.annotation.Require;
import me.hieu.libraries.drink.annotation.Sender;
import me.hieu.libraries.util.CC;
import org.bukkit.command.CommandSender;

/**
 * Author: Le Thanh Hieu
 * Date: 19/10/2024
 */

public class PermissionCommand {

    @Command(name = "add", desc = "update profile permissions", usage = "<profile> <permission>")
    @Require("core.permissions")
    public void add(@Sender CommandSender sender, Profile target, String permission){
        if (target.getPermissions().contains(permission)){
            sender.sendMessage(CC.translate(target.getFormattedName() + " &calready has the permission '" + permission + "'."));
            return;
        }
        ProfileUpdatePermissionsPacket packet = new ProfileUpdatePermissionsPacket(target.getUniqueId(), permission, true);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

    @Command(name = "remove", desc = "update profile permissions", usage = "<profile> <permission>")
    @Require("core.permissions")
    public void remove(@Sender CommandSender sender, Profile target, String permission){
        if (!target.getPermissions().contains(permission)){
            sender.sendMessage(CC.translate(target.getFormattedName() + " &cdoesn't have the permission '" + permission + "'."));
            return;
        }
        ProfileUpdatePermissionsPacket packet = new ProfileUpdatePermissionsPacket(target.getUniqueId(), permission, false);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

}
