package me.hieu.core.profile;

import me.hieu.core.Core;
import me.hieu.core.Locale;
import me.hieu.core.packet.BroadcastPacket;
import me.hieu.core.punishment.PunishmentType;
import me.hieu.libraries.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 06/10/2024
 */

public class ProfileListener implements Listener {

    @EventHandler
    public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        Profile profile = new Profile(event.getUniqueId());
        String message;
        if (profile.getActivePunishment(PunishmentType.BLACKLIST) != null){
            message = PunishmentType.BLACKLIST.get();
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CC.translate(message));
        }
        if (profile.getActivePunishment(PunishmentType.PERM_BAN) != null){
            message = PunishmentType.BLACKLIST.get();
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CC.translate(message));
        }
        if (profile.getActivePunishment(PunishmentType.TEMP_BAN) != null) {
            message = PunishmentType.TEMP_BAN.get().replace("{duration}", profile.getActivePunishment(PunishmentType.TEMP_BAN).getExpiresIn());
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CC.translate(message));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Core plugin = Core.getInstance();
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();
        Profile profile = new Profile(uniqueId);
        profile.setName(player.getName());
        profile.save();
        plugin.getProfileHandler().getProfiles().add(profile);
        profile.calibratePermissions();
        profile.updateDisguise();
        String join = CC.translate(Locale.STAFF_JOIN.get())
                .replace("{profile}", profile.getFormattedName())
                .replace("{server}", Bukkit.getName());
        BroadcastPacket packet = new BroadcastPacket(join, "*");
        plugin.getRedisHandler().sendPacket(packet);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event){
        Core plugin = Core.getInstance();
        ProfileHandler handler = plugin.getProfileHandler();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Profile profile = handler.getProfileByUniqueId(uuid);
        profile.save();
        handler.getProfiles().remove(profile);
        String join = CC.translate(Locale.STAFF_LEAVE.get())
                .replace("{profile}", profile.getFormattedName())
                .replace("{server}", Bukkit.getName());
        BroadcastPacket packet = new BroadcastPacket(join, "*");
        plugin.getRedisHandler().sendPacket(packet);
    }

}
