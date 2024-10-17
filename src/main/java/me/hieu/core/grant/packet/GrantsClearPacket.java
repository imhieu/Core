package me.hieu.core.grant.packet;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.grant.Grant;
import me.hieu.core.profile.Profile;
import me.hieu.core.redis.packet.Packet;
import me.hieu.libraries.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 10/10/2024
 */

@Getter
public class GrantsClearPacket extends Packet {

    private final UUID target;

    public GrantsClearPacket(UUID target){
        this.target = target;
    }

    @Override
    public void onReceived() {
        Profile profile = Core.getInstance().getProfileHandler().getProfileByUniqueId(target);
        for (Grant grant : profile.getGrants()){
            if (grant.getRank().getName().equalsIgnoreCase("Default")) continue;
            profile.getGrants().remove(grant);
        }
        profile.save();
        Player player = Bukkit.getPlayer(target);
        if (player == null) return;
        player.sendMessage(CC.translate("&cYour grants have been cleared."));
    }

    @Override
    public void onSend() {

    }

}
