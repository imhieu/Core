package me.hieu.core.profile.permission.packet;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.Locale;
import me.hieu.core.profile.Profile;
import me.hieu.core.redis.packet.Packet;
import me.hieu.libraries.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 19/10/2024
 */

@Getter
public class ProfileUpdatePermissionsPacket extends Packet {

    private UUID uniqueId;
    private String permission;
    private boolean add;

    public ProfileUpdatePermissionsPacket(UUID uniqueId, String permission, boolean add){
        this.uniqueId = uniqueId;
        this.permission = permission;
        this.add = add;
    }

    @Override
    public void onReceived() {
        Profile profile = Core.getInstance().profileHandler.getProfileByUniqueId(uniqueId);
        if (add){
            profile.getPermissions().add(permission);
        } else {
            profile.getPermissions().remove(permission);
        }
        profile.save();
        Core.broadcast(CC.translate(Locale.PROFILE_UPDATE.get().replace("{profile}", profile.getFormattedName())), "*");
        Player player = Bukkit.getPlayer(uniqueId);
        if (player == null) return;
        profile.calibratePermissions();
    }

    @Override
    public void onSend() {

    }

}
