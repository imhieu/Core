package me.hieu.core.grant.command;

import me.hieu.core.Core;
import me.hieu.core.grant.packet.GrantAddPacket;
import me.hieu.core.profile.Profile;
import me.hieu.core.rank.Rank;
import me.hieu.core.util.ConsoleUtil;
import me.hieu.core.util.TimeUtil;
import me.hieu.libraries.drink.annotation.Command;
import me.hieu.libraries.drink.annotation.Require;
import me.hieu.libraries.drink.annotation.Sender;
import me.hieu.libraries.drink.annotation.Text;
import me.hieu.libraries.util.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 07/10/2024
 */

public class GrantCommand {

    @Command(name = "", desc = "grant rank", usage = "<player> <rank> <duration> <reason>")
    @Require("core.grant")
    public void command(@Sender CommandSender sender, Profile target, Rank rank, String time, @Text String reason){
        UUID addedBy;
        if (sender instanceof Player){
            addedBy = ((Player) sender).getUniqueId();
        } else {
            addedBy = ConsoleUtil.CONSOLE_UUID;
        }
        if (target.getActiveGrant().getRank().getUniqueId().equals(rank.getUniqueId())){
            sender.sendMessage(CC.translate(target.getFormattedName() + " &calready has an active &r" + rank.getFormattedRank() + " &crank."));
            return;
        }
        long duration = TimeUtil.convertString(time);
        if (duration == -1){
            sender.sendMessage(CC.translate("&cNo duration with the format '" + TimeUtil.convertLong(duration) + "'."));
            return;
        }
        sender.sendMessage(CC.translate("&aGranted &r" + target.getFormattedName() + "&a the &r" + rank.getFormattedRank() + "&a rank " + (duration == Integer.MAX_VALUE ? "permanently" : "for " + TimeUtil.convertLong(duration)) + "."));
        GrantAddPacket packet = new GrantAddPacket(target.getUniqueId(), UUID.randomUUID(), rank.getUniqueId(), addedBy, duration, reason);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

}
