package me.hieu.core.listener;

import me.hieu.core.Core;
import me.hieu.core.Locale;
import me.hieu.core.profile.Profile;
import me.hieu.libraries.util.CC;
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
        event.setFormat(CC.translate(Locale.CHAT_FORMAT.get().replace("{profile}", profile.getChatFormattedName())).replace("{message}", event.getMessage()));
    }

}
