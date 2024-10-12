package me.hieu.core.command;

import me.hieu.core.Core;
import me.hieu.core.packet.BroadcastPacket;
import me.hieu.core.redis.RedisHandler;
import me.hieu.libraries.drink.annotation.*;
import me.hieu.libraries.util.CC;
import org.bukkit.command.CommandSender;

/**
 * Author: Le Thanh Hieu
 * Date: 08/10/2024
 */

public class AlertCommand {

    @Command(name = "", desc = "broadcast message", usage = "<message>")
    @Require("*")
    public void command(@Sender CommandSender sender, @Text String message, @Flag('r') boolean raw){
        RedisHandler handler = Core.getInstance().getRedisHandler();
        if (raw){
            BroadcastPacket packet = new BroadcastPacket(CC.translate(message), "");
            handler.sendPacket(packet);
            return;
        }
        BroadcastPacket packet = new BroadcastPacket(CC.translate("&7[&4Alert&7] &r" + message), "");
        handler.sendPacket(packet);
    }

}
