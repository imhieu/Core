package me.hieu.core.punishment.command;

import me.hieu.core.Core;
import me.hieu.core.punishment.PunishmentType;
import me.hieu.core.punishment.packet.PunishmentAddPacket;
import me.hieu.core.util.ConsoleUtil;
import me.hieu.libraries.drink.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 12/10/2024
 */

public class KickCommand {

    PunishmentType type = PunishmentType.KICK;

    @Command(name = "", desc = "kick player", usage = "<player> <reason> [-s]")
    @Require("core.kick")
    public void command(@Sender CommandSender sender, Player target, @Text String reason, @Flag('s') boolean silent){
        UUID addedBy;
        if (sender instanceof Player){
            addedBy = ((Player) sender).getUniqueId();
        } else {
            addedBy = ConsoleUtil.CONSOLE_UUID;
        }
        PunishmentAddPacket packet = new PunishmentAddPacket(target.getUniqueId(), !silent, type, UUID.randomUUID(), addedBy, System.currentTimeMillis(), Integer.MIN_VALUE, reason);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

}
