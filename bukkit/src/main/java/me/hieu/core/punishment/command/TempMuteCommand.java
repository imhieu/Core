package me.hieu.core.punishment.command;

import me.hieu.core.Core;
import me.hieu.core.profile.Profile;
import me.hieu.core.punishment.PunishmentType;
import me.hieu.core.punishment.packet.PunishmentAddPacket;
import me.hieu.core.util.ConsoleUtil;
import me.hieu.core.util.TimeUtil;
import me.hieu.libraries.drink.annotation.*;
import me.hieu.libraries.util.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 12/10/2024
 */

public class TempMuteCommand {

    PunishmentType type = PunishmentType.TEMP_MUTE;

    @Command(name = "", desc = "temp mute player", usage = "<player> <duration> <reason> [-s]")
    @Require("*")
    public void command(@Sender CommandSender sender, Profile target, String time, @Text String reason, @Flag('s') boolean silent){
        UUID addedBy;
        if (sender instanceof Player){
            addedBy = ((Player) sender).getUniqueId();
        } else {
            addedBy = ConsoleUtil.CONSOLE_UUID;
        }
        if (target.getActivePunishment(PunishmentType.PERM_MUTE) != null){
            sender.sendMessage(CC.translate(target.getFormattedName() + " &cis already muted."));
            return;
        }
        if (target.getActivePunishment(type) != null){
            sender.sendMessage(CC.translate(target.getFormattedName() + " &cis already muted."));
            return;
        }
        long duration = TimeUtil.convertString(time);
        if (duration == -1){
            sender.sendMessage(CC.translate("&cNo duration found with the time '" + time + "'."));
            return;
        } else if (duration == Integer.MAX_VALUE){
            sender.sendMessage(CC.translate("&cDuration exceeds the temporarily duration with time '" + time + "'."));
            return;
        }
        PunishmentAddPacket packet = new PunishmentAddPacket(target.getUniqueId(), !silent, type, UUID.randomUUID(), addedBy, System.currentTimeMillis(), duration, reason);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

}
