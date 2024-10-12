package me.hieu.core.grant;

import java.util.List;

/**
 * Author: Le Thanh Hieu
 * Date: 01/10/2024
 */

public class GrantSerializer {

    public static String convert(List<Grant> grants){
        StringBuilder builder = new StringBuilder();
        for (Grant grant : grants){
            builder.append(grant.getUniqueId())
                    .append(":")
                    .append(grant.getGrantRank().getUniqueId())
                    .append(":")
                    .append(grant.getAddedBy().toString())
                    .append(":")
                    .append(grant.getAddedOn())
                    .append(":")
                    .append(grant.getDuration())
                    .append(":")
                    .append(grant.getAddedReason())
                    .append(":")
                    .append(grant.isPardoned());
            if (grant.isPardoned()) {
                builder.append(":")
                        .append(grant.getPardonedBy().toString())
                        .append(":")
                        .append(grant.getPardonedOn())
                        .append(":")
                        .append(grant.getPardonedReason());
            }
            builder.append(";");
        }
        return builder.toString();
    }

}
