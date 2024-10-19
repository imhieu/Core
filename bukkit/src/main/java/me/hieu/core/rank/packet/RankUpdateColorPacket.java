package me.hieu.core.rank.packet;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.Locale;
import me.hieu.core.rank.Rank;
import me.hieu.core.rank.RankHandler;
import me.hieu.core.redis.packet.Packet;
import me.hieu.libraries.util.CC;
import org.bukkit.ChatColor;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 03/10/2024
 */

@Getter
public class RankUpdateColorPacket extends Packet {

    private final UUID rankUniqueId;
    private final ChatColor color;

    public RankUpdateColorPacket(UUID rankUniqueId, ChatColor color){
        this.rankUniqueId = rankUniqueId;
        this.color = color;
    }

    @Override
    public void onReceived() {
        RankHandler rankHandler = Core.getInstance().getRankHandler();
        Rank rank = rankHandler.getRankByUniqueId(rankUniqueId);
        rank.setColor(color);
        rank.save();
        Core.broadcast(CC.translate(Locale.RANK_UPDATE.get().replace("{rank}", rank.getFormattedRank())), "*");
    }

    @Override
    public void onSend() {

    }

}
