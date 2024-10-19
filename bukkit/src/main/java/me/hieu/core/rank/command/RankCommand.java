package me.hieu.core.rank.command;

import me.hieu.core.Core;
import me.hieu.core.rank.Rank;
import me.hieu.core.rank.packet.*;
import me.hieu.libraries.drink.annotation.Command;
import me.hieu.libraries.drink.annotation.Require;
import me.hieu.libraries.drink.annotation.Sender;
import me.hieu.libraries.util.CC;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Author: Le Thanh Hieu
 * Date: 03/10/2024
 */

public class RankCommand {

    @Command(name = "create", desc = "create new rank", usage = "<name>")
    @Require("*")
    public void create(@Sender CommandSender sender, String name){
        Rank rank = Core.getInstance().getRankHandler().getRankByName(name);
        if (rank != null){
            sender.sendMessage(CC.translate("&cThere's already a rank with the name '" + name + "'."));
            return;
        }
        RankCreatePacket packet = new RankCreatePacket(name.replaceAll("_", " "));
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

    @Command(name = "setcolor", desc = "update rank color", usage = "<rank> <color>")
    @Require("*")
    public void color(@Sender CommandSender sender, Rank rank, ChatColor color){
        RankUpdateColorPacket packet = new RankUpdateColorPacket(rank.getUniqueId(), color);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

    @Command(name = "setweight", desc = "update rank weight", usage = "<rank> <weight>")
    @Require("*")
    public void weight(@Sender CommandSender sender, Rank rank, int weight){
        RankUpdateWeightPacket packet = new RankUpdateWeightPacket(rank.getUniqueId(), weight);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

    @Command(name = "setprefix", desc = "update rank prefix", usage = "<rank> <prefix>")
    @Require("*")
    public void prefix(@Sender CommandSender sender, Rank rank, String prefix){
        RankUpdatePrefixPacket packet = new RankUpdatePrefixPacket(rank.getUniqueId(), prefix);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

    @Command(name = "setsuffix", desc = "update rank suffix", usage = "<rank> <suffix>")
    @Require("*")
    public void suffix(@Sender CommandSender sender, Rank rank, String suffix){
        RankUpdateSuffixPacket packet = new RankUpdateSuffixPacket(rank.getUniqueId(), suffix);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

    @Command(name = "addperm", desc = "update rank permissions", usage = "<rank> <permission>")
    @Require("*")
    public void addperm(@Sender CommandSender sender, Rank rank, String permission){
        if (rank.getPermissions().contains(permission)){
            sender.sendMessage(CC.translate("&cThe rank with the name '" + rank.getName() + "' already has the permission '" + permission + "'."));
            return;
        }
        RankUpdatePermissions packet = new RankUpdatePermissions(rank.getUniqueId(), permission, true);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

    @Command(name = "removeperm", desc = "update rank permissions", usage = "<rank> <permission>")
    @Require("*")
    public void removeperm(@Sender CommandSender sender, Rank rank, String permission){
        if (!rank.getPermissions().contains(permission)){
            sender.sendMessage(CC.translate("&cThe rank with the name '" + rank.getName() + "' doesn't have the permission '" + permission + "'."));
            return;
        }
        RankUpdatePermissions packet = new RankUpdatePermissions(rank.getUniqueId(), permission, false);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

    @Command(name = "inherit", desc = "update rank inheritances", usage = "<parent> <child>")
    @Require("*")
    public void inherit(@Sender CommandSender sender, Rank parentRank, Rank childRank){
        if (parentRank == childRank){
            sender.sendMessage(CC.translate("&cThe parent rank can't be the same as the child rank."));
            return;
        }
        if (parentRank.getInheritances().contains(childRank.getUniqueId())){
            sender.sendMessage(CC.translate("&cThe parent rank with the name '" + parentRank.getName() + "' is already inheriting the child rank with the name '" + childRank.getName() + "'."));
            return;
        }
        RankUpdateInheritancePacket packet = new RankUpdateInheritancePacket(parentRank.getUniqueId(), childRank.getUniqueId(), true);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

    @Command(name = "uninherit", desc = "update rank inheritances", usage = "<parent> <child>")
    @Require("*")
    public void uninherit(@Sender CommandSender sender, Rank parentRank, Rank childRank){
        if (!parentRank.getInheritances().contains(childRank.getUniqueId())){
            sender.sendMessage(CC.translate("&cThe parent rank with the name '" + parentRank.getName() + "' isn't inheriting the child rank with the name '" + childRank.getName() + "'."));
            return;
        }
        RankUpdateInheritancePacket packet = new RankUpdateInheritancePacket(parentRank.getUniqueId(), childRank.getUniqueId(), false);
        Core.getInstance().getRedisHandler().sendPacket(packet);
    }

    @Command(name = "delete", desc = "delete rank", usage = "<rank>")
    @Require("*")
    public void delete(@Sender CommandSender sender, Rank rank){
        Core plugin = Core.getInstance();
        if (rank == null){
            sender.sendMessage(CC.translate("&cCan't find a rank with that name."));
            return;
        }
        if (rank.getName().equalsIgnoreCase(plugin.getConfigHandler().getDefaultConfigz().getString("default-rank.name"))){
            sender.sendMessage(CC.translate("&cCan't delete the default rank."));
            return;
        }
        RankDeletePacket packet = new RankDeletePacket(rank.getUniqueId());
        plugin.getRedisHandler().sendPacket(packet);
    }

}
