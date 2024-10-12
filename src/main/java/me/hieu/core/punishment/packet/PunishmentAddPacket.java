package me.hieu.core.punishment.packet;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.Locale;
import me.hieu.core.packet.BroadcastPacket;
import me.hieu.core.profile.Profile;
import me.hieu.core.punishment.Punishment;
import me.hieu.core.punishment.PunishmentType;
import me.hieu.core.redis.packet.Packet;
import me.hieu.core.util.ConsoleUtil;
import me.hieu.libraries.util.CC;
import me.hieu.libraries.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 11/10/2024
 */

@Getter
public class PunishmentAddPacket extends Packet {

    private UUID target;
    private boolean silent;
    private PunishmentType type;
    private UUID uniqueId;
    private UUID addedBy;
    private long addedOn;
    private long duration;
    private String addedReason;

    public PunishmentAddPacket(UUID target, boolean silent, PunishmentType type, UUID uniqueId, UUID addedBy, long addedOn, long duration, String addedReason){
        this.target = target;
        this.silent = silent;
        this.type = type;
        this.uniqueId = uniqueId;
        this.addedBy = addedBy;
        this.addedOn = addedOn;
        this.duration = duration;
        this.addedReason = addedReason;
    }

    @Override
    public void onReceived() {
        Profile targetProfile = Core.getInstance().getProfileHandler().getProfileByUniqueId(target);
        Punishment punishment = new Punishment(type, uniqueId, addedBy, addedOn, duration, addedReason);
        targetProfile.getPunishments().add(punishment);
        targetProfile.save();
        String addedByName;
        if (addedBy.equals(ConsoleUtil.CONSOLE_UUID)){
            addedByName = "&4Console";
        } else {
            addedByName = Core.getInstance().getProfileHandler().getProfileByUniqueId(addedBy).getFormattedName();
        }
        String message;
        if (silent){
            message = CC.translate(Locale.PUNISHMENT_SILENT_BROADCAST.get()
                    .replace("{target}", targetProfile.getFormattedName())
                    .replace("{context}", type.getPunished())
                    .replace("{executor}", addedByName)
            );
        } else {
            message = CC.translate(Locale.PUNISHMENT_BROADCAST.get()
                    .replace("{target}", targetProfile.getFormattedName())
                    .replace("{context}", type.getPunished())
                    .replace("{executor}", addedByName)
            );
        }
        BroadcastPacket packet = new BroadcastPacket(message, "");
        Core.getInstance().getRedisHandler().sendPacket(packet);
        Player player = Bukkit.getPlayer(target);
        if (player == null) return;
        String punishMessage = type.get();
        switch (type){
            case BLACKLIST:
            case PERM_BAN:
                TaskUtil.runTask(() -> {
                    player.kickPlayer(CC.translate(punishMessage));
                });
                break;
            case TEMP_BAN:
                TaskUtil.runTask(() -> {
                    player.kickPlayer(CC.translate(punishMessage.replace("{duration}", punishment.getExpiresIn())));
                });
                break;
            case KICK:
                TaskUtil.runTask(() -> {
                    player.kickPlayer(CC.translate(punishMessage.replace("{reason}", addedReason)));
                });
                break;
            case PERM_MUTE:
                player.sendMessage(CC.translate(punishMessage.replace("{executor}", addedByName).replace("{reason}", addedReason)));
                break;
            case TEMP_MUTE:
                player.sendMessage(CC.translate(punishMessage.replace("{executor}", addedByName).replace("{duration}", punishment.getPunishmentDuration()).replace("{reason}", addedReason)));
                break;
            case WARN:
                break;
        }
    }

    @Override
    public void onSend() {

    }

}
