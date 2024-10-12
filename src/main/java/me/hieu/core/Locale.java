package me.hieu.core;

import lombok.Getter;

/**
 * Author: Le Thanh Hieu
 * Date: 03/10/2024
 */

@Getter
public enum Locale {

    CHAT_FORMAT ("chat-format"),
    RANK_CREATE ("packets.rank.create"),
    RANK_UPDATE ("packets.rank.update"),
    RANK_DELETE ("packets.rank.delete"),
    STAFF_JOIN ("packets.staff.join"),
    STAFF_LEAVE ("packets.staff.leave"),
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
