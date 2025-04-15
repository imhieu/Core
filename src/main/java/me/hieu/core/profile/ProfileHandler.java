package me.hieu.core.profile;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import me.hieu.core.Core;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 06/10/2024
 */

@Getter
public class ProfileHandler {

    private MongoCollection<Document> collection;
    private List<Profile> profiles;

    public ProfileHandler(){
        collection = Core.getInstance().getMongoHandler().getDatabase().getCollection("Profiles");
        profiles = new ArrayList<>();
    }

    public Profile getProfileByName(String name){
        Player player = Bukkit.getPlayer(name);
        if (player != null){
            for (Profile profile : profiles){
                if (profile.getName().equalsIgnoreCase(name)) return profile;
            }
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
        if (offlinePlayer.hasPlayedBefore()) return new Profile(name);
        return null;
    }

    public Profile getProfileByUniqueId(UUID uniqueId){
        Player player = Bukkit.getPlayer(uniqueId);
        if (player != null) {
            for (Profile profile : profiles){
                if (profile.getUniqueId().equals(uniqueId)) return profile;
            }
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uniqueId);
        if (offlinePlayer.hasPlayedBefore()) return new Profile(uniqueId);
        return null;
    }

}
