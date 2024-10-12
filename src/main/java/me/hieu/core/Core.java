package me.hieu.core;

import lombok.Getter;
import me.hieu.core.color.ChatColorHandler;
import me.hieu.core.color.ChatColorProvider;
import me.hieu.core.command.AlertCommand;
import me.hieu.core.grant.command.ClearGrantsCommand;
import me.hieu.core.grant.command.GrantCommand;
import me.hieu.core.grant.command.GrantsCommand;
import me.hieu.core.handler.ConfigHandler;
import me.hieu.core.listener.ChatListener;
import me.hieu.core.mongo.MongoHandler;
import me.hieu.core.profile.Profile;
import me.hieu.core.profile.ProfileHandler;
import me.hieu.core.profile.ProfileListener;
import me.hieu.core.profile.ProfileProvider;
import me.hieu.core.punishment.command.*;
import me.hieu.core.punishment.command.pardon.UnbanCommand;
import me.hieu.core.punishment.command.pardon.UnblacklistCommand;
import me.hieu.core.punishment.command.pardon.UnmuteCommand;
import me.hieu.core.punishment.history.ClearHistoryCommand;
import me.hieu.core.punishment.history.HistoryCommand;
import me.hieu.core.rank.Rank;
import me.hieu.core.rank.RankHandler;
import me.hieu.core.rank.RankProvider;
import me.hieu.core.rank.command.RankCommand;
import me.hieu.core.rank.command.RanksCommand;
import me.hieu.core.redis.RedisHandler;
import me.hieu.libraries.Libraries;
import me.hieu.libraries.drink.CommandService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Core extends JavaPlugin {

    @Getter public static Core instance;

    public ConfigHandler configHandler;
    public MongoHandler mongoHandler;
    public RedisHandler redisHandler;
    public ChatColorHandler chatColorHandler;
    public RankHandler rankHandler;
    public ProfileHandler profileHandler;

    @Override
    public void onEnable() {
        instance = this;
        initHandlers();
        initCommands();
        initListeners();
    }

    private void initHandlers(){
        configHandler = new ConfigHandler();
        mongoHandler = new MongoHandler();
        redisHandler = new RedisHandler();
        chatColorHandler = new ChatColorHandler();
        rankHandler = new RankHandler();
        rankHandler.initRanks();
        profileHandler = new ProfileHandler();
    }

    private void initCommands(){
        CommandService drink = Libraries.get(this);
        drink.bind(ChatColor.class).toProvider(new ChatColorProvider());
        drink.bind(Rank.class).toProvider(new RankProvider());
        drink.bind(Profile.class).toProvider(new ProfileProvider());
        drink.register(new RankCommand(), "rank");
        drink.register(new RanksCommand(), "ranks");
        drink.register(new GrantCommand(), "grant");
        drink.register(new GrantsCommand(), "grants");
        drink.register(new ClearGrantsCommand(), "cleargrants");
        drink.register(new AlertCommand(), "alert", "broadcast", "bc");
        drink.register(new HistoryCommand(), "history");
        drink.register(new BlacklistCommand(), "blacklist");
        drink.register(new BanCommand(), "ban");
        drink.register(new TempBanCommand(), "tempban");
        drink.register(new KickCommand(), "kick");
        drink.register(new MuteCommand(), "mute");
        drink.register(new TempMuteCommand(), "tempmute");
        drink.register(new WarnCommand(), "warn");
        drink.register(new UnblacklistCommand(), "unblacklist");
        drink.register(new UnbanCommand(), "unban");
        drink.register(new UnmuteCommand(), "unmute");
        drink.register(new ClearHistoryCommand(), "clearhistory");
        drink.registerCommands();
    }

    private void initListeners(){
        Bukkit.getServer().getPluginManager().registerEvents(new ProfileListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static void broadcast(String message, String permission){
        for (Player player : Bukkit.getOnlinePlayers()){
            if (permission.isEmpty()){
                player.sendMessage(message);
            } else {
                if (player.hasPermission(permission)){
                    player.sendMessage(message);
                }
            }
        }
    }

}
