package me.hieu.core.punishment;

import lombok.Getter;
import lombok.Setter;
import me.hieu.core.util.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 01/10/2024
 */

@Getter @Setter
public class Punishment implements Comparable<Punishment> {

    private PunishmentType type;
    private UUID uniqueId;
    private UUID addedBy;
    private long addedOn;
    private long duration;
    private String addedReason;
    private boolean pardoned;
    private UUID pardonedBy;
    private long pardonedOn;
    private String pardonedReason;

    public Punishment(PunishmentType type, UUID uniqueId, UUID addedBy, long addedOn, long duration, String addedReason){
        this.type = type;
        this.uniqueId = uniqueId;
        this.addedBy = addedBy;
        this.addedOn = addedOn;
        this.duration = duration;
        this.addedReason = addedReason;
        pardoned = false;
    }

    public Punishment(PunishmentType type, UUID uniqueId, UUID addedBy, long addedOn, long duration, String addedReason, boolean pardoned, UUID pardonedBy, long pardonedOn, String pardonedReason){
        this.type = type;
        this.uniqueId = uniqueId;
        this.addedBy = addedBy;
        this.addedOn = addedOn;
        this.duration = duration;
        this.addedReason = addedReason;
        this.pardoned = pardoned;
        this.pardonedBy = pardonedBy;
        this.pardonedOn = pardonedOn;
        this.pardonedReason = pardonedReason;
    }

    @Override
    public int compareTo(@NotNull Punishment o) {
        return (int) (o.getAddedOn() - addedOn);
    }

    public long getExpireDate(){
        return addedOn + duration;
    }

    public boolean isExpired(){
        return System.currentTimeMillis() > getExpireDate();
    }

    public String getExpiresIn(){
        if (duration == Integer.MAX_VALUE) return "Never";
        if (duration == Integer.MIN_VALUE) return "Already";
        return TimeUtil.convertLong(getExpireDate() - System.currentTimeMillis());
    }

    public String getPunishmentDuration(){
        if (duration == Integer.MAX_VALUE) return "Permanent";
        if (duration == Integer.MIN_VALUE) return "Temporary";
        return TimeUtil.convertLong(duration);
    }

}
