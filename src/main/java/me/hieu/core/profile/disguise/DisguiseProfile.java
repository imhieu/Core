package me.hieu.core.profile.disguise;

import lombok.Getter;
import lombok.Setter;
import me.hieu.core.Core;
import me.hieu.core.rank.Rank;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 16/10/2024
 */

@Getter @Setter
public class DisguiseProfile {

    private String name;
    private UUID rankUniqueId;

    public DisguiseProfile(String name, UUID rankUniqueId){
        this.name = name;
        this.rankUniqueId = rankUniqueId;
    }

    public Rank getRank(){
        return Core.getInstance().getRankHandler().getRankByUniqueId(rankUniqueId);
    }

}
