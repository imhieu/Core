package me.hieu.core.listener;

import me.hieu.core.Core;
import me.hieu.core.Locale;
import me.hieu.core.packet.BroadcastPacket;
import me.hieu.core.profile.Profile;
import me.hieu.core.punishment.Punishment;
import me.hieu.core.punishment.PunishmentType;
import me.hieu.libraries.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Author: Le Thanh Hieu
 * Date: 07/10/2024
 */

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        Profile profile = Core.getInstance().getProfileHandler().getProfileByUniqueId(player.getUniqueId());
        Punishment permMutePunishment = profile.getActivePunishment(PunishmentType.PERM_MUTE);
        if (permMutePunishment != null){
            player.sendMessage(CC.translate("&cYou are permanently muted."));
            event.setCancelled(true);
            return;
        }
        Punishment tempMutePunishment = profile.getActivePunishment(PunishmentType.TEMP_MUTE);
        if (tempMutePunishment != null){
            player.sendMessage(CC.translate("&cYou are muted for another &e" + tempMutePunishment.getExpiresIn() + "&c."));
            event.setCancelled(true);
            return;
        }
        if (profile.getStaffOption().isStaffChatEnabled() && player.hasPermission("*")){
            String message = CC.translate(Locale.STAFF_CHAT.get().replace("{server}", Bukkit.getName()).replace("{profile}", profile.getFormattedName())).replace("{message}", event.getMessage());
            BroadcastPacket packet = new BroadcastPacket(message, "*");
            Core.getInstance().getRedisHandler().sendPacket(packet);
            event.setCancelled(true);
            return;
        }
        event.setFormat(CC.translate(Locale.CHAT_FORMAT.get().replace("{profile}", profile.getChatFormattedName())).replace("{message}", event.getMessage()));
    }

}
