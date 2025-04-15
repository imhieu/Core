package me.hieu.core;

import lombok.Getter;

/**
 * Author: Le Thanh Hieu
 * Date: 03/10/2024
 */

@Getter
public enum Locale {

    CHAT_FORMAT ("chat-format"),

    PROFILE_CREATE ("packets.profile.create"),
    PROFILE_UPDATE ("packets.profile.update"),

    RANK_CREATE ("packets.rank.create"),
    RANK_UPDATE ("packets.rank.update"),
    RANK_DELETE ("packets.rank.delete"),

    TAG_CREATE ("packets.tag.create"),
    TAG_UPDATE ("packets.tag.update"),
    TAG_DELETE ("packets.tag.delete"),

    STAFF_JOIN ("packets.staff.join"),
    STAFF_LEAVE ("packets.staff.leave"),
    STAFF_CHAT ("packets.staff.chat"),

    PUNISHMENT_BROADCAST("packets.punishment.broadcast"),
    PUNISHMENT_SILENT_BROADCAST("packets.punishment.silent-broadcast");

    Locale (String path){
        this.path = path;
    }

    private final String path;

    public String get(){
        return Core.getInstance().getConfigHandler().getLangConfigz().getString(path);
    }

}
