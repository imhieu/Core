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
public class RankUpdatePrefixPacket extends Packet {

    private final UUID rankUniqueId;
    private final String prefix;

    public RankUpdatePrefixPacket(UUID rankUniqueId, String prefix){
        this.rankUniqueId = rankUniqueId;
        this.prefix = prefix;
    }

    @Override
    public void onReceived() {
        Rank rank = Core.getInstance().getRankHandler().getRankByUniqueId(rankUniqueId);
        rank.setPrefix(prefix);
        rank.save();
        Core.broadcast(CC.translate(Locale.RANK_UPDATE.get().replace("{rank}", rank.getFormattedRank())), "core.ranks");
    }

    @Override
    public void onSend() {

    }

}
