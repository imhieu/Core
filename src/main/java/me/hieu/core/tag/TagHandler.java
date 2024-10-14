package me.hieu.core.tag;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import me.hieu.core.Core;
import org.bson.Document;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 12/10/2024
 */

@Getter
public class TagHandler {

    public MongoCollection<Document> collection;
    public List<Tag> tags;

    public TagHandler(){
        collection = Core.getInstance().getMongoHandler().getDatabase().getCollection("Tags");
        tags = new ArrayList<>();
    }

    public void initTags(){
        for (Document document : collection.find()){
            UUID uniqueId = UUID.fromString(document.getString("uniqueId"));
            String name = document.getString("name");
            ChatColor color = ChatColor.valueOf(document.getString("color"));
            String display = document.getString("display");
            new Tag(uniqueId, name, color, display);
        }
    }

    public Tag getTagByName(String name){
        for (Tag tag : tags){
            if (tag.getName().equalsIgnoreCase(name)) return tag;
        }
        return null;
    }

    public Tag getTagByUniqueId(UUID uniqueId){
        for (Tag tag : tags){
            if (tag.getUniqueId().equals(uniqueId)) return tag;
        }
        return null;
    }

}
