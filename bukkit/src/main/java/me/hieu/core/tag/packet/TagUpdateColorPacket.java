package me.hieu.core.tag.packet;

import me.hieu.core.Core;
import me.hieu.core.Locale;
import me.hieu.core.redis.packet.Packet;
import me.hieu.core.tag.Tag;
import me.hieu.libraries.util.CC;
import org.bukkit.ChatColor;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 12/10/2024
 */

public class TagUpdateColorPacket extends Packet {

    private UUID tagUniqueId;
    private ChatColor color;

    public TagUpdateColorPacket(UUID tagUniqueId, ChatColor color){
        this.tagUniqueId = tagUniqueId;
        this.color = color;
    }

    @Override
    public void onReceived() {
        Tag tag = Core.getInstance().getTagHandler().getTagByUniqueId(tagUniqueId);
        tag.setColor(color);
        tag.save();
        Core.broadcast(CC.translate(Locale.TAG_UPDATE.get().replace("{tag}", tag.getFormattedName())), "core.tags");
    }

    @Override
    public void onSend() {

    }

}
