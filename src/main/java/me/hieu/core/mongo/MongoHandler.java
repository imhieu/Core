package me.hieu.core.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.hieu.core.Core;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Author: Le Thanh Hieu
 * Date: 01/10/2024
 */

@Getter
public class MongoHandler {

    private final MongoClient client;
    private final MongoDatabase database;

    public MongoHandler(){
        FileConfiguration defaultConfigz = Core.getInstance().getConfigHandler().getDefaultConfigz();
        client = new MongoClient(new MongoClientURI(defaultConfigz.getString("database.mongo.uri")));
        database = client.getDatabase(defaultConfigz.getString("database.mongo.database"));
    }

}
