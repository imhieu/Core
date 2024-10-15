package me.hieu.core.grant;

import lombok.Getter;
import lombok.Setter;
import me.hieu.core.Core;
import me.hieu.core.rank.Rank;
import me.hieu.core.util.ConsoleUtil;
import me.hieu.core.util.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 01/10/2024
 */

@Getter @Setter
public class Grant implements Comparable<Grant> {

    private UUID uniqueId;
    private UUID rankUniqueId;
    private UUID addedBy;
    private long addedOn;
    private long duration;
    private String addedReason;
    private boolean pardoned;
    private UUID pardonedBy;
    private long pardonedOn;
    private String pardonedReason;

    public Grant(UUID uniqueId, UUID rankUniqueId, UUID addedBy, long addedOn, long duration, String addedReason){
        this.uniqueId = uniqueId;
        this.rankUniqueId = rankUniqueId;
        this.addedBy = addedBy;
        this.addedOn = addedOn;
        this.duration = duration;
        this.addedReason = addedReason;
        pardoned = false;
        pardonedBy = ConsoleUtil.CONSOLE_UUID;
        pardonedOn = System.currentTimeMillis();
        pardonedReason = "";
    }

    public Grant(UUID uniqueId, UUID rankUniqueId, UUID addedBy, long addedOn, long duration, String addedReason, boolean pardoned, UUID pardonedBy, long pardonedOn, String pardonedReason){
        this.uniqueId = uniqueId;
        this.rankUniqueId = rankUniqueId;
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
    public int compareTo(@NotNull Grant o) {
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
        return TimeUtil.convertLong(getExpireDate() - System.currentTimeMillis());
    }

    public String getGrantDuration(){
        if (duration == Integer.MAX_VALUE) return "Permanent";
        return TimeUtil.convertLong(duration);
    }

    public Rank getGrantRank(){
        return Core.getInstance().getRankHandler().getRankByUniqueId(rankUniqueId);
    }

}
