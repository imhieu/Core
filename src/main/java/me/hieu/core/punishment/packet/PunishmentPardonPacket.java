package me.hieu.core.punishment.packet;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.Locale;
import me.hieu.core.packet.BroadcastPacket;
import me.hieu.core.profile.Profile;
import me.hieu.core.punishment.Punishment;
import me.hieu.core.redis.packet.Packet;
import me.hieu.core.util.ConsoleUtil;
import me.hieu.libraries.util.CC;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 11/10/2024
 */

@Getter
public class PunishmentPardonPacket extends Packet {

    private UUID target;
    private UUID punishmentUniqueId;
    private boolean silent;
    private UUID pardonedBy;
    private long pardonedOn;
    private String pardonedReason;

    public PunishmentPardonPacket(UUID target, UUID punishmentUniqueId, boolean silent, UUID pardonedBy, long pardonedOn, String pardonedReason){
        this.target = target;
        this.punishmentUniqueId = punishmentUniqueId;
        this.silent = silent;
        this.pardonedBy = pardonedBy;
        this.pardonedOn = pardonedOn;
        this.pardonedReason = pardonedReason;
    }

    @Override
    public void onReceived() {
        Profile targetProfile = Core.getInstance().getProfileHandler().getProfileByUniqueId(target);
        Punishment punishment = null;
        for (Punishment profilePunishment : targetProfile.getPunishments()){
            if (profilePunishment.getUniqueId().equals(punishmentUniqueId)){
                profilePunishment.setPardoned(true);
                profilePunishment.setPardonedBy(pardonedBy);
                profilePunishment.setPardonedOn(pardonedOn);
                profilePunishment.setPardonedReason(pardonedReason);
                punishment = profilePunishment;
            }
        }
        targetProfile.save();
        String pardonedByName;
        if (pardonedBy.equals(ConsoleUtil.CONSOLE_UUID)){
            pardonedByName = "&4Console";
        } else {
            pardonedByName = Core.getInstance().getProfileHandler().getProfileByUniqueId(pardonedBy).getFormattedName();
        }
        String message;
        if (silent){
            message = CC.translate(Locale.PUNISHMENT_SILENT_BROADCAST.get()
                    .replace("{target}", targetProfile.getFormattedName())
                    .replace("{context}", punishment.getType().getPardoned())
                    .replace("{executor}", pardonedByName)
            );
        } else {
            message = CC.translate(Locale.PUNISHMENT_BROADCAST.get()
                    .replace("{target}", targetProfile.getFormattedName())
                    .replace("{context}", punishment.getType().getPardoned())
                    .replace("{executor}", pardonedByName)
            );
        }
        BroadcastPacket packet = new BroadcastPacket(message, "");
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

    @Override
    public void onSend() {

    }

}
