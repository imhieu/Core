package me.hieu.core.tag.packet;

import me.hieu.core.Core;
import me.hieu.core.Locale;
import me.hieu.core.redis.packet.Packet;
import me.hieu.core.tag.Tag;
import me.hieu.libraries.util.CC;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 12/10/2024
 */

public class TagUpdateDisplayPacket extends Packet {

    private UUID tagUniqueId;
    private String display;

    public TagUpdateDisplayPacket(UUID tagUniqueId, String display){
        this.tagUniqueId = tagUniqueId;
        this.display = display;
    }

    @Override
    public void onReceived() {
        Tag tag = Core.getInstance().getTagHandler().getTagByUniqueId(tagUniqueId);
        tag.setDisplay(display);
        tag.save();
        Core.broadcast(CC.translate(Locale.TAG_UPDATE.get().replace("{tag}", tag.getFormattedName())), "*");
    }

    @Override
    public void onSend() {

    }

}
