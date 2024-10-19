package me.hieu.core.rank.packet;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.Locale;
import me.hieu.core.rank.Rank;
import me.hieu.core.redis.packet.Packet;
import me.hieu.libraries.util.CC;

/**
 * Author: Le Thanh Hieu
 * Date: 03/10/2024
 */

@Getter
public class RankCreatePacket extends Packet {

    private final String name;

    public RankCreatePacket(String name){
        this.name = name;
    }

    @Override
    public void onReceived() {
        Rank rank = new Rank(name);
        rank.save();
        Core.broadcast(CC.translate(Locale.RANK_CREATE.get().replace("{rank}", rank.getFormattedRank())), "*");
    }

    @Override
    public void onSend() {

    }

}
