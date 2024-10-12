package me.hieu.core.punishment.history.packet;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.profile.Profile;
import me.hieu.core.redis.packet.Packet;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 12/10/2024
 */

@Getter
public class HistoryClearPacket extends Packet {

    private UUID target;

    public HistoryClearPacket(UUID target){
        this.target = target;
    }

    @Override
    public void onReceived() {
        Profile profile = Core.getInstance().getProfileHandler().getProfileByUniqueId(target);
        profile.getPunishments().clear();
        profile.save();
    }

    @Override
    public void onSend() {

    }

}
