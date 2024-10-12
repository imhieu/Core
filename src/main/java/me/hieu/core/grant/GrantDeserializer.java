package me.hieu.core.grant;

import me.hieu.core.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 05/10/2024
 */

public class GrantDeserializer {

    public static List<Grant> convert(String string){
        List<Grant> grants = new ArrayList<>();
        String[] splitGrants = string.split(";");
        for (String grant : splitGrants){
            String[] split = grant.split(":");
            UUID uniqueId = UUID.fromString(split[0]);
            UUID rankUniqueId = UUID.fromString(split[1]);
            if (Core.getInstance().getRankHandler().getRankByUniqueId(rankUniqueId) == null) continue;
            UUID addedBy = UUID.fromString(split[2]);
            long addedDate = Long.valueOf(split[3]);
            long duration = Long.valueOf(split[4]);
            String addedReason = split[5];
            boolean pardoned = Boolean.valueOf(split[6]);
            if (!pardoned){
                grants.add(new Grant(uniqueId, rankUniqueId, addedBy, addedDate, duration, addedReason));
                continue;
            }
            UUID pardonedBy = UUID.fromString(split[7]);
            long pardonedDate = Long.valueOf(split[8]);
            String pardonedReason = split[9];
            grants.add(new Grant(uniqueId, rankUniqueId, addedBy, addedDate, duration, addedReason, pardoned, pardonedBy, pardonedDate, pardonedReason));
        }
        return grants;
    }

}
