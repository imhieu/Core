package me.hieu.core.punishment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 05/10/2024
 */

public class PunishmentDeserializer {

    public static List<Punishment> convert(String string){
        List<Punishment> punishments = new ArrayList<>();
        String[] splitPunishments = string.split(";");
        for (String punishment : splitPunishments){
            String[] split = punishment.split(":");
            PunishmentType type = PunishmentType.getTypeByContext(split[0]);
            UUID uniqueId = UUID.fromString(split[1]);
            UUID addedBy = UUID.fromString(split[2]);
            long addedDate = Long.valueOf(split[3]);
            long duration = Long.valueOf(split[4]);
            String addedReason = split[5];
            boolean pardoned = Boolean.valueOf(split[6]);
            if (!pardoned){
                punishments.add(new Punishment(type, uniqueId, addedBy, addedDate, duration, addedReason));
                continue;
            }
            UUID pardonedBy = UUID.fromString(split[7]);
            long pardonedDate = Long.valueOf(split[8]);
            String pardonedReason = split[9];
            punishments.add(new Punishment(type, uniqueId, addedBy, addedDate, duration, addedReason, pardoned, pardonedBy, pardonedDate, pardonedReason));
        }
        return punishments;
    }

}
