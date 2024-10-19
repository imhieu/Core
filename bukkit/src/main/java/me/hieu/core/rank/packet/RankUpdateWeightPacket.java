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
public class RankUpdateWeightPacket extends Packet {

    private final UUID rankUniqueId;
    private final int weight;

    public RankUpdateWeightPacket(UUID rankUniqueId, int weight){
        this.rankUniqueId = rankUniqueId;
        this.weight = weight;
    }

    @Override
    public void onReceived() {
        Rank rank = Core.getInstance().rankHandler.getRankByUniqueId(rankUniqueId);
        rank.setWeight(weight);
        rank.save();
        Core.broadcast(CC.translate(Locale.RANK_UPDATE.get().replace("{rank}", rank.getFormattedRank())), "*");
    }

    @Override
    public void onSend() {

    }

}
