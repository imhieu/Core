package me.hieu.core.punishment;

import java.util.List;

/**
 * Author: Le Thanh Hieu
 * Date: 01/10/2024
 */

public class PunishmentSerializer {

    public static String convert(List<Punishment> punishments){
        StringBuilder builder = new StringBuilder();
        for (Punishment punishment : punishments){
            builder.append(punishment.getType().getContext())
                    .append(":")
                    .append(punishment.getUniqueId())
                    .append(":")
                    .append(punishment.getAddedBy().toString())
                    .append(":")
                    .append(punishment.getAddedOn())
                    .append(":")
                    .append(punishment.getDuration())
                    .append(":")
                    .append(punishment.getAddedReason())
                    .append(":")
                    .append(punishment.isPardoned());
            if (punishment.isPardoned()) {
                builder.append(":")
                        .append(punishment.getPardonedBy().toString())
                        .append(":")
                        .append(punishment.getPardonedOn())
                        .append(":")
                        .append(punishment.getPardonedReason());
            }
            builder.append(";");
        }
        return builder.toString();
    }

}
