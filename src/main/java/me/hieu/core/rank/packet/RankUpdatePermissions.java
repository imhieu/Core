package me.hieu.core.rank.packet;

import lombok.Getter;
import me.hieu.core.Core;
import me.hieu.core.Locale;
import me.hieu.core.profile.Profile;
import me.hieu.core.rank.Rank;
import me.hieu.core.redis.packet.Packet;
import me.hieu.libraries.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 03/10/2024
 */

@Getter
public class RankUpdatePermissions extends Packet {

    private final UUID rankUniqueId;
    private final String permission;
    private final boolean add;

    public RankUpdatePermissions(UUID rankUniqueId, String permission, boolean add){
        this.rankUniqueId = rankUniqueId;
        this.permission = permission;
        this.add = add;
    }

    @Override
    public void onReceived() {
        Rank rank = Core.getInstance().getRankHandler().getRankByUniqueId(rankUniqueId);
        if (add){
            rank.getPermissions().add(permission);
        } else {
            rank.getPermissions().remove(permission);
        }
        rank.save();
        Core.broadcast(CC.translate(Locale.RANK_UPDATE.get().replace("{rank}", rank.getFormattedRank())), "core.ranks");
        for (Player player : Bukkit.getOnlinePlayers()){
            Profile profile = Core.getInstance().getProfileHandler().getProfileByUniqueId(player.getUniqueId());
            if (profile.getActiveGrant().getRank().getName().equalsIgnoreCase(rank.getName())){
                profile.calibratePermissions();
            }
        }
    }

    @Override
    public void onSend() {

    }

}
