package me.hieu.core.rank.packet;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.Locale;
import me.hieu.core.profile.Profile;
import me.hieu.core.rank.Rank;
import me.hieu.core.rank.RankHandler;
import me.hieu.core.redis.packet.Packet;
import me.hieu.libraries.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 03/10/2024
 */

@Getter
public class RankUpdateInheritancePacket extends Packet {

    private final UUID parentRankUniqueId, childRankUniqueId;
    private final boolean inherit;

    public RankUpdateInheritancePacket(UUID parentRankUniqueId, UUID childRankUniqueId, boolean inherit){
        this.parentRankUniqueId = parentRankUniqueId;
        this.childRankUniqueId = childRankUniqueId;
        this.inherit = inherit;
    }

    @Override
    public void onReceived() {
        RankHandler rankHandler = Core.getInstance().getRankHandler();
        Rank parentRank = rankHandler.getRankByUniqueId(parentRankUniqueId);
        if (inherit){
            parentRank.getInheritances().add(childRankUniqueId);
        } else {
            parentRank.getInheritances().remove(childRankUniqueId);
        }
        parentRank.save();
        Core.broadcast(CC.translate(Locale.RANK_UPDATE.get().replace("{rank}", parentRank.getFormattedRank())), "*");
        for (Player player : Bukkit.getOnlinePlayers()){
            Profile profile = Core.getInstance().getProfileHandler().getProfileByUniqueId(player.getUniqueId());
            if (profile.getActiveGrant().getRank().getUniqueId().equals(parentRankUniqueId)){
                profile.calibratePermissions();
            }
        }
    }

    @Override
    public void onSend() {

    }

}
