package me.hieu.core.packet;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.redis.packet.Packet;

/**
 * Author: Le Thanh Hieu
 * Date: 08/10/2024
 */

@Getter
public class BroadcastPacket extends Packet {

    private final String message;
    private final String permission;

    public BroadcastPacket(String message, String permission){
        this.message = message;
        this.permission = permission;
    }

    @Override
    public void onReceived() {
        Core.broadcast(message, permission);
    }

    @Override
    public void onSend() {

    }

}
