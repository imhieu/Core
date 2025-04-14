package me.hieu.core.punishment.history;

import me.hieu.core.Core;
import me.hieu.core.profile.Profile;
import me.hieu.core.punishment.history.packet.HistoryClearPacket;
import me.hieu.libraries.drink.annotation.Command;
import me.hieu.libraries.drink.annotation.Require;
import me.hieu.libraries.drink.annotation.Sender;
import me.hieu.libraries.util.CC;
import org.bukkit.command.CommandSender;

/**
 * Author: Le Thanh Hieu
 * Date: 12/10/2024
 */

public class ClearHistoryCommand {

    @Command(name = "", desc = "clear history", usage = "<player>")
    @Require("core.history")
    public void command(@Sender CommandSender sender, Profile target){
        sender.sendMessage(CC.translate("&aHistory of &r" + target.getFormattedName() + " &ahas been cleared."));
        HistoryClearPacket packet = new HistoryClearPacket(target.getUniqueId());
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

}
