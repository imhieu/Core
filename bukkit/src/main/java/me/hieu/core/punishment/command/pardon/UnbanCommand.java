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

public class UnbanCommand {

    PunishmentType permBanType = PunishmentType.PERM_BAN;
    PunishmentType tempBanType = PunishmentType.TEMP_BAN;

    @Command(name = "", desc = "unban player", usage = "<player> <reason> [-s]")
    @Require("core.unban")
    public void command(@Sender CommandSender sender, Profile target, @Text String reason, @Flag('s') boolean silent){
        UUID pardonedBy;
        if (sender instanceof Player){
            pardonedBy = ((Player) sender).getUniqueId();
        } else {
            pardonedBy = ConsoleUtil.CONSOLE_UUID;
        }
        Punishment permbanPunishment = target.getActivePunishment(permBanType);
        Punishment tempBanPunishment = target.getActivePunishment(tempBanType);
        if (permbanPunishment == null && tempBanPunishment == null){
            sender.sendMessage(CC.translate(target.getFormattedName() + " &cisn't banned."));
        }
        if (permbanPunishment != null){
            PunishmentPardonPacket packet = new PunishmentPardonPacket(target.getUniqueId(), permbanPunishment.getUniqueId(), !silent, pardonedBy, System.currentTimeMillis(), reason);
            Core.getInstance().getRedisHandler().sendPacket(packet);
        }
        if (tempBanPunishment != null){
            PunishmentPardonPacket packet = new PunishmentPardonPacket(target.getUniqueId(), tempBanPunishment.getUniqueId(), !silent, pardonedBy, System.currentTimeMillis(), reason);
            Core.getInstance().getRedisHandler().sendPacket(packet);
        }
    }

}
