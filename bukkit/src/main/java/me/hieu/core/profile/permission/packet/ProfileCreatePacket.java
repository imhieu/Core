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
public class ProfileCreatePacket extends Packet {

    private UUID uniqueId;

    public ProfileCreatePacket(UUID uniqueId){
        this.uniqueId = uniqueId;
    }

    @Override
    public void onReceived() {
        Profile profile = Core.getInstance().profileHandler.getProfileByUniqueId(uniqueId);
        Core.broadcast(CC.translate(Locale.PROFILE_CREATE.get().replace("{profile}", profile.getFormattedName())), "core.profiles");
    }

    @Override
    public void onSend() {

    }

}
