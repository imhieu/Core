package me.hieu.core.grant.packet;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.grant.Grant;
import me.hieu.core.profile.Profile;
import me.hieu.core.profile.ProfileHandler;
import me.hieu.core.rank.Rank;
import me.hieu.core.rank.RankHandler;
import me.hieu.core.redis.packet.Packet;
import me.hieu.libraries.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 14/10/2024
 */

@Getter
public class GrantPardonPacket extends Packet {

    private UUID target;
    private UUID uniqueId;
    private UUID rankUniqueId;
    private UUID pardonedBy;
    private long pardonedOn;
    private String pardonedReason;

    public GrantPardonPacket(UUID target, UUID uniqueId, UUID rankUniqueId, UUID pardonedBy, long pardonedOn, String pardonedReason){
        this.target = target;
        this.uniqueId = uniqueId;
        this.rankUniqueId = rankUniqueId;
        this.pardonedBy = pardonedBy;
        this.pardonedOn = pardonedOn;
        this.pardonedReason = pardonedReason;
    }

    @Override
    public void onReceived() {
        Core plugin = Core.getInstance();
        ProfileHandler profileHandler = plugin.getProfileHandler();
        RankHandler rankHandler = plugin.getRankHandler();
        Profile profile = profileHandler.getProfileByUniqueId(target);
        Grant profileGrant = null;
        for (Grant grant : profile.getGrants()){
            if (grant.getUniqueId().equals(uniqueId) && grant.getRankUniqueId().equals(rankUniqueId)){
                grant.setPardoned(true);
                grant.setPardonedBy(pardonedBy);
                grant.setPardonedOn(pardonedOn);
                grant.setPardonedReason(pardonedReason);
                profileGrant = grant;
            }
        }
        profile.save();
        Player player = Bukkit.getPlayer(target);
        if (player == null) return;
        profile.calibratePermissions();
        Rank rank = rankHandler.getRankByUniqueId(profileGrant.getRankUniqueId());
        player.sendMessage(CC.translate("&aYour &r" + rank.getFormattedRank() + " &arank was pardoned by &r" + profileHandler.getProfileByUniqueId(profileGrant.getPardonedBy()).getFormattedName() + "&a."));
    }

    @Override
    public void onSend() {

    }

}
