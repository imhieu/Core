package me.hieu.core.rank;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import me.hieu.core.Core;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 02/10/2024
 */

@Getter @Setter
public class Rank implements Comparable<Rank> {

    private UUID uniqueId;
    private String name;
    private ChatColor color;
    private int weight;
    private String prefix, suffix;
    private List<String> permissions;
    private List<UUID> inheritances;

    public Rank(String name){
        uniqueId = UUID.randomUUID();
        this.name = name;
        color = ChatColor.WHITE;
        weight = 0;
        prefix = "";
        suffix = "";
        permissions = new ArrayList<>();
        inheritances = new ArrayList<>();
        Core.getInstance().getRankHandler().getRanks().add(this);
    }

    public Rank(UUID uniqueId, String name, ChatColor color, int weight, String prefix, String suffix, List<String> permissions, List<UUID> inheritances){
        this.uniqueId = uniqueId;
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.prefix = prefix;
        this.suffix = suffix;
        this.permissions = permissions;
        this.inheritances = inheritances;
        Core.getInstance().getRankHandler().getRanks().add(this);
    }

    @Override
    public int compareTo(@NotNull Rank o) {
        return o.getWeight() - weight;
    }

    public List<String> getAllPermissions(){
        List<String> permissions = new ArrayList<>(this.permissions);
        for (UUID rankUniqueId : inheritances){
            Rank rank = Core.getInstance().rankHandler.getRankByUniqueId(rankUniqueId);
            permissions.addAll(rank.getAllPermissions());
        }
        return permissions;
    }

    public String getFormattedRank(){
        return color + name;
    }

    public void save(){
        Document document = new Document();
        document.append("uniqueId", uniqueId.toString());
        document.append("name", name);
        document.append("color", color.name());
        document.append("weight", weight);
        document.append("prefix", prefix);
        document.append("suffix", suffix);
        if (!permissions.isEmpty()){
            StringBuilder builder = new StringBuilder();
            for (String permission : permissions){
                builder.append(permission).append(":");
            }
            document.append("permission", builder.toString());
        }
        if (!inheritances.isEmpty()){
            StringBuilder builder = new StringBuilder();
            for (UUID uuid : inheritances){
                builder.append(uuid.toString()).append(":");
            }
            document.append("inheritances", builder.toString());
        }
        Bson filter = Filters.eq("uniqueId", uniqueId.toString());
        Core.getInstance().getRankHandler().getCollection().replaceOne(filter, document, new ReplaceOptions().upsert(true));
    }

    public void delete(){
        Bson filter = Filters.eq("uniqueId", uniqueId.toString());
        Core.getInstance().getRankHandler().getCollection().deleteOne(filter);
        Core.getInstance().getRankHandler().getRanks().remove(this);
    }

}
