package me.hieu.core.rank;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import lombok.Getter;
import me.hieu.core.Core;
import org.bson.Document;
import org.bukkit.ChatColor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 02/10/2024
 */

@Getter
public class RankHandler {

    private MongoCollection<Document> collection;
    private List<Rank> ranks;

    public RankHandler(){
        collection = Core.getInstance().getMongoHandler().getDatabase().getCollection("Ranks");
        ranks = new ArrayList<>();
    }

    public void initRanks(){
        for (Document document : collection.find()){
            UUID uniqueId = UUID.fromString(document.getString("uniqueId"));
            String name = document.getString("name");
            ChatColor color = ChatColor.valueOf(document.getString("color"));
            int weight = document.getInteger("weight");
            String prefix = document.getString("prefix");
            String suffix = document.getString("suffix");
            List<String> permissions = new ArrayList<>();
            Gson gson = new Gson();
            Type type;
            if (document.getString("permissions") != null){
                type = new TypeToken<List<String>>(){}.getType();
                permissions = gson.fromJson(document.getString("permissions"), type);
            }
            List<UUID> inheritances = new ArrayList<>();
            if (document.getString("inheritances") != null){
                type = new TypeToken<List<UUID>>(){}.getType();
                inheritances = gson.fromJson(document.getString("inheritances"), type);
            }
            new Rank(uniqueId, name, color, weight, prefix, suffix, permissions, inheritances);
        }
        Rank rank = getRankByName("Default");
        if (rank == null){
            new Rank("Default").save();
        }
    }

    public Rank getRankByName(String name){
        for (Rank rank : ranks){
            if (rank.getName().equalsIgnoreCase(name)) return rank;
        }
        return null;
    }

    public Rank getRankByUniqueId(UUID uniqueId){
        for (Rank rank : ranks){
            if (rank.getUniqueId().equals(uniqueId)) return rank;
        }
        return null;
    }

}
