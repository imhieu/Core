package me.hieu.core.command;

import me.hieu.core.Core;
import me.hieu.core.profile.Profile;
import me.hieu.core.rank.Rank;
import me.hieu.libraries.drink.annotation.Command;
import me.hieu.libraries.drink.annotation.Sender;
import me.hieu.libraries.util.CC;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Le Thanh Hieu
 * Date: 15/10/2024
 */

public class ListCommand {

    @Command(name = "", desc = "players list")
    public void command(@Sender CommandSender sender){
        List<Rank> ranks = Core.getInstance().getRankHandler().getRanks();
        Collections.sort(ranks);
        List<String> formattedRankNames = new ArrayList<>();
        for (Rank rank : ranks){
            formattedRankNames.add(rank.getColor() + rank.getName());
        }
        List<Profile> profiles = Core.getInstance().getProfileHandler().getProfiles();
        Collections.sort(profiles);
        List<String> formattedProfileNames = new ArrayList<>();
        for (Profile profile : profiles){
            formattedProfileNames.add(profile.getFormattedName());
        }
        sender.sendMessage(CC.translate(StringUtils.join(formattedRankNames, "&f, ")));
        sender.sendMessage(CC.translate("&f(" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ") [" + StringUtils.join(formattedProfileNames, "&f, ") + "&f]"));
    }

}
