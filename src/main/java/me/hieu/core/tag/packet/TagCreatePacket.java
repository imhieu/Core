package me.hieu.core.tag.packet;

import me.hieu.core.Core;
import me.hieu.core.Locale;
import me.hieu.core.redis.packet.Packet;
import me.hieu.core.tag.Tag;
import me.hieu.libraries.util.CC;

/**
 * Author: Le Thanh Hieu
 * Date: 12/10/2024
 */

public class TagCreatePacket extends Packet {

    private String name;

    public TagCreatePacket(String name){
        this.name = name;
    }

    @Override
    public void onReceived() {
        Tag tag = new Tag(name);
        tag.save();
        Core.broadcast(CC.translate(Locale.TAG_CREATE.get().replace("{tag}", tag.getFormattedName())), "*");
    }

    @Override
    public void onSend() {

    }

}
