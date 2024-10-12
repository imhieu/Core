package me.hieu.core.grant.packet;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.grant.Grant;
import me.hieu.core.profile.Profile;
import me.hieu.core.rank.Rank;
import me.hieu.core.redis.packet.Packet;
import me.hieu.libraries.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 07/10/2024
 */

@Getter
public class GrantAddPacket extends Packet {

    private final UUID target;
    private final UUID uniqueId;
    private final UUID rankUniqueId;
    private final UUID addedBy;
    private final long duration;
    private final String reason;

    public GrantAddPacket(UUID target, UUID uniqueId, UUID rankUniqueId, UUID addedBy, long duration, String reason){
        this.target = target;
        this.uniqueId = uniqueId;
        this.rankUniqueId = rankUniqueId;
        this.addedBy = addedBy;
        this.duration = duration;
        this.reason = reason;
    }

    @Override
    public void onReceived() {
        Profile profile = Core.getInstance().getProfileHandler().getProfileByUniqueId(target);
        Grant grant = new Grant(uniqueId, rankUniqueId, addedBy, System.currentTimeMillis(), duration, reason);
        profile.getGrants().add(grant);
        profile.save();
        Player player = Bukkit.getPlayer(target);
        if (player == null) return;
        profile.calibratePermissions();
        Rank rank = Core.getInstance().getRankHandler().getRankByUniqueId(rankUniqueId);
        player.sendMessage(CC.translate("&aYou've been granted the &r" + rank.getFormattedRank() + " &arank " + (duration == Integer.MAX_VALUE ? "permanently" : "for " + grant.getExpiresIn()) + "."));
    }

    @Override
    public void onSend() {

    }

}
