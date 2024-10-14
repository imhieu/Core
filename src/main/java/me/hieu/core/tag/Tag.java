package me.hieu.core.tag;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import me.hieu.core.Core;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.ChatColor;

import java.util.UUID;

/**
 * Author: Le Thanh Hieu
 * Date: 12/10/2024
 */

@Getter @Setter
public class Tag {

    private UUID uniqueId;
    private String name;
    private ChatColor color;
    private String display;

    public Tag(String name){
        uniqueId = UUID.randomUUID();
        this.name = name;
        color = ChatColor.WHITE;
        display = "";
        Core.getInstance().getTagHandler().getTags().add(this);
    }

    public Tag(UUID uniqueId, String name, ChatColor color, String display){
        this.uniqueId = uniqueId;
        this.name = name;
        this.color = color;
        this.display = display;
        Core.getInstance().getTagHandler().getTags().add(this);
    }

    public String getFormattedName(){
        return color + name;
    }

    public void save(){
        Document document = new Document();
        document.append("uniqueId", uniqueId.toString());
        document.append("name", name);
        document.append("color", color.name());
        document.append("display", display);
        Bson filter = Filters.eq("uniqueId", uniqueId.toString());
        Core.getInstance().getTagHandler().getCollection().replaceOne(filter, document, new ReplaceOptions().upsert(true));
    }

    public void delete(){
        Bson filter = Filters.eq("uniqueId", uniqueId.toString());
        Core.getInstance().getTagHandler().getCollection().deleteOne(filter);
        Core.getInstance().getTagHandler().getTags().remove(this);
    }

}
