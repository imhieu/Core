package me.hieu.core.rank.packet;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.Locale;
import me.hieu.core.rank.Rank;
import me.hieu.core.redis.packet.Packet;
import me.hieu.libraries.util.CC;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 03/10/2024
 */

@Getter
public class RankDeletePacket extends Packet {

    private final UUID rankUniqueId;

    public RankDeletePacket(UUID rankUniqueId){
        this.rankUniqueId = rankUniqueId;
    }

    @Override
    public void onReceived() {
        Rank rank = Core.getInstance().getRankHandler().getRankByUniqueId(rankUniqueId);
        Core.broadcast(CC.translate(Locale.RANK_DELETE.get().replace("{rank}", rank.getFormattedRank())), "*");
        rank.delete();
    }

    @Override
    public void onSend() {

    }

}
