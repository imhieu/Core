package me.hieu.core.punishment.command.pardon;

import me.hieu.core.Core;
import me.hieu.core.profile.Profile;
import me.hieu.core.punishment.Punishment;
import me.hieu.core.punishment.PunishmentType;
import me.hieu.core.punishment.packet.PunishmentPardonPacket;
import me.hieu.core.util.ConsoleUtil;
import me.hieu.libraries.drink.annotation.*;
import me.hieu.libraries.util.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 12/10/2024
 */

public class UnmuteCommand {

    PunishmentType permMuteType = PunishmentType.PERM_MUTE;
    PunishmentType tempMuteType = PunishmentType.TEMP_MUTE;

    @Command(name = "", desc = "unban player", usage = "<player> <reason> [-s]")
    @Require("*")
    public void command(@Sender CommandSender sender, Profile target, @Text String reason, @Flag('s') boolean silent){
        UUID pardonedBy;
        if (sender instanceof Player){
            pardonedBy = ((Player) sender).getUniqueId();
        } else {
            pardonedBy = ConsoleUtil.CONSOLE_UUID;
        }
        Punishment permMutePunishment = target.getActivePunishment(permMuteType);
        Punishment tempMutePunishment = target.getActivePunishment(tempMuteType);
        if (permMutePunishment == null && tempMutePunishment == null){
            sender.sendMessage(CC.translate(target.getFormattedName() + " &cisn't muted."));
        }
        if (permMutePunishment != null){
            PunishmentPardonPacket packet = new PunishmentPardonPacket(target.getUniqueId(), permMutePunishment.getUniqueId(), !silent, pardonedBy, System.currentTimeMillis(), reason);
            Core.getInstance().getRedisHandler().sendPacket(packet);
        }
        if (tempMutePunishment != null){
            PunishmentPardonPacket packet = new PunishmentPardonPacket(target.getUniqueId(), tempMutePunishment.getUniqueId(), !silent, pardonedBy, System.currentTimeMillis(), reason);
            Core.getInstance().getRedisHandler().sendPacket(packet);
        }
    }

}
