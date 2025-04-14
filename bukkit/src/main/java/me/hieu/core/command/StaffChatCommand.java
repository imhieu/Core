package me.hieu.core.command;

import me.hieu.core.Core;
import me.hieu.core.Locale;
import me.hieu.core.packet.BroadcastPacket;
import me.hieu.core.profile.Profile;
import me.hieu.libraries.drink.annotation.*;
import me.hieu.libraries.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Author: Le Thanh Hieu
 * Date: 13/10/2024
 */

public class StaffChatCommand {

    @Command(name = "", desc = "staff chat", usage = "[message]")
    @Require("core.staff")
    public void command(@Sender Player player, @OptArg() @Text String message){
        Profile profile = Core.getInstance().getProfileHandler().getProfileByUniqueId(player.getUniqueId());
        if (message.isEmpty()){
            profile.getStaffOption().setStaffChatEnabled(!profile.getStaffOption().isStaffChatEnabled());
            player.sendMessage(CC.translate(profile.getStaffOption().isStaffChatEnabled() ? "&aYou've enabled staff chat." : "&cYou've disabled staff chat."));
            return;
        }
        String broadcastMessage = CC.translate(Locale.STAFF_CHAT.get().replace("{server}", Bukkit.getName()).replace("{profile}", profile.getFormattedName())).replace("{message}", message);
        BroadcastPacket packet = new BroadcastPacket(broadcastMessage, "core.staff");
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

}
