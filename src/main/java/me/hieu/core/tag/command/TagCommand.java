package me.hieu.core.tag.command;

import me.hieu.core.Core;
import me.hieu.core.tag.Tag;
import me.hieu.core.tag.packet.TagCreatePacket;
import me.hieu.core.tag.packet.TagUpdateColorPacket;
import me.hieu.core.tag.packet.TagUpdateDisplayPacket;
import me.hieu.libraries.drink.annotation.Command;
import me.hieu.libraries.drink.annotation.Require;
import me.hieu.libraries.drink.annotation.Sender;
import me.hieu.libraries.util.CC;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Author: Le Thanh Hieu
 * Date: 12/10/2024
 */

public class TagCommand {

    @Command(name = "create", desc = "create tag", usage = "<name>")
    @Require("*")
    public void create(@Sender CommandSender sender, String name){
        Tag tag = Core.getInstance().getTagHandler().getTagByName(name);
        if (tag != null){
            sender.sendMessage(CC.translate("&cThere's already a tag with the name '" + name + "'."));
            return;
        }
        TagCreatePacket packet = new TagCreatePacket(name);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

    @Command(name = "setcolor", desc = "update tag color", usage = "<tag> <color>")
    @Require("*")
    public void color(@Sender CommandSender sender, Tag tag, ChatColor color){
        TagUpdateColorPacket packet = new TagUpdateColorPacket(tag.getUniqueId(), color);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

    @Command(name = "setdisplay", desc = "update tag display", usage = "<tag> <display>")
    @Require("*")
    public void display(@Sender CommandSender sender, Tag tag, String display){
        TagUpdateDisplayPacket packet = new TagUpdateDisplayPacket(tag.getUniqueId(), display);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

}
