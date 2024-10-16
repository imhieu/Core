package me.hieu.core.profile.disguise;

import lombok.Getter;
import lombok.Setter;
import me.hieu.core.rank.Rank;

/**
 * Author: Le Thanh Hieu
 * Date: 16/10/2024
 */

@Getter @Setter
public class DisguiseProfile {

    private String name;
    private String skin;
    private Rank rank;

    public DisguiseProfile(String name, String skin, Rank rank){
        this.name = name;
        this.skin = skin;
        this.rank = rank;
    }

}
