package me.hieu.core.punishment.command;

import me.hieu.core.Core;
import me.hieu.core.profile.Profile;
import me.hieu.core.punishment.PunishmentType;
import me.hieu.core.punishment.packet.PunishmentAddPacket;
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

public class MuteCommand {

    PunishmentType type = PunishmentType.PERM_MUTE;

    @Command(name = "", desc = "perm mute player", usage = "<player> <reason> [-s]")
    @Require("core.mute")
    public void command(@Sender CommandSender sender, Profile target, @Text String reason, @Flag('s') boolean silent){
        UUID addedBy;
        if (sender instanceof Player){
            addedBy = ((Player) sender).getUniqueId();
        } else {
            addedBy = ConsoleUtil.CONSOLE_UUID;
        }
        if (target.getActivePunishment(type) != null){
            sender.sendMessage(CC.translate(target.getFormattedName() + " &cis already muted."));
            return;
        }
        if (target.getActivePunishment(PunishmentType.TEMP_MUTE) != null){
            sender.sendMessage(CC.translate(target.getFormattedName() + " &cis already muted."));
            return;
        }
        PunishmentAddPacket packet = new PunishmentAddPacket(target.getUniqueId(), !silent, type, UUID.randomUUID(), addedBy, System.currentTimeMillis(), Integer.MAX_VALUE, reason);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

}
