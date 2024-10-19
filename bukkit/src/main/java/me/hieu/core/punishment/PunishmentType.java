package me.hieu.core.punishment;

import lombok.Getter;
import me.hieu.core.Core;
import org.bukkit.ChatColor;

/**
 * Author: Le Thanh Hieu
 * Date: 05/10/2024
 */

@Getter
public enum PunishmentType {

    BLACKLIST ("Blacklist", "blacklisted", "unblacklisted", ChatColor.DARK_RED, "packets.punishment.message.blacklist"),
    PERM_BAN ("Perm Ban", "permanently banned", "unbanned", ChatColor.RED, "packets.punishment.message.perm-ban"),
    TEMP_BAN ("Temp Ban", "temporarily banned", "unbanned", ChatColor.RED, "packets.punishment.message.temp-ban"),
    KICK ("Kick", "kicked", "", ChatColor.GOLD, "packets.punishment.message.kick"),
    PERM_MUTE ("Perm Mute", "permanently muted", "unmuted", ChatColor.YELLOW, "packets.punishment.message.perm-mute"),
    TEMP_MUTE ("Temp Mute", "temporarily muted", "unmuted", ChatColor.YELLOW, "packets.punishment.message.temp-mute"),
    WARN ("Warn", "warned", "", ChatColor.GREEN, "packets.punishment.message.warn");

    private final String context;
    private final String punished;
    private final String pardoned;
    private final ChatColor color;
    private final String path;

    PunishmentType(String context, String punished, String pardoned, ChatColor color, String path){
        this.context = context;
        this.punished = punished;
        this.pardoned = pardoned;
        this.color = color;
        this.path = path;
    }

    public static PunishmentType getTypeByContext(String context){
        for (PunishmentType type : PunishmentType.values()){
            if (type.getContext().equalsIgnoreCase(context)) return type;
        }
        return null;
    }

    public String getFormattedContext(){
        return color + context;
    }

    public String getFormattedPunished(){
        return color + punished;
    }

    public String getFormattedPardoned(){
        return color + pardoned;
    }

    public String get(){
        return Core.getInstance().getConfigHandler().getLangConfigz().getString(path);
    }

}
