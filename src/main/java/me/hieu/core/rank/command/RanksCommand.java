package me.hieu.core.rank.command;

import me.hieu.core.Core;
import me.hieu.core.rank.Rank;
import me.hieu.libraries.drink.annotation.Command;
import me.hieu.libraries.drink.annotation.Require;
import me.hieu.libraries.drink.annotation.Sender;
import me.hieu.libraries.util.CC;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

/**
 * Author: Le Thanh Hieu
 * Date: 03/10/2024
 */

public class RanksCommand {

    @Command(name = "", desc = "view all ranks")
    @Require("core.ranks")
    public void command(@Sender CommandSender sender){
        List<Rank> ranks = Core.getInstance().getRankHandler().getRanks();
        Collections.sort(ranks);
        int size = ranks.size();
        sender.sendMessage(CC.CHAT_BAR);
        sender.sendMessage(CC.translate("&6Available Rank" + (size == 1 ? "" : "s") + " &7(" + size + "):"));
        sender.sendMessage(CC.CHAT_BAR);
        for (Rank rank : ranks){
            sender.sendMessage(CC.translate("&7- &r" + rank.getFormattedRank() + " &f(Weight: " + rank.getWeight() + ")"));
        }
        sender.sendMessage(CC.CHAT_BAR);
    }

}
