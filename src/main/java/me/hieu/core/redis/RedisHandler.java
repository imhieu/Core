package me.hieu.core.redis;

import com.google.gson.Gson;
import me.hieu.core.Core;
import me.hieu.core.redis.packet.Packet;
import org.bukkit.configuration.file.FileConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.function.Consumer;

public class RedisHandler {

    private String uri;

    private JedisPool jedisPool;

    private String channel;

    private Gson gson;

    public RedisHandler() {
        FileConfiguration config = Core.getInstance().getConfigHandler().getDefaultConfigz();
        this.uri = config.getString("database.redis.uri");
        this.channel = config.getString("database.redis.channel");
        this.gson = new Gson();
        connect();
    }

    /**
     * Attempts to make a connection to the
     * redis database with the specified credentials and
     * starts a thread for receiving messages
     */
    public void connect() {
        this.jedisPool = new JedisPool(uri);
        new Thread(() -> {
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        String[] strings = message.split("//");
                        Object jsonObject = null;
                        try {
                            jsonObject = gson.fromJson(strings[1], Class.forName(strings[0]));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        Packet packet = (Packet) jsonObject;
                        packet.onReceived();
                    }
                }, channel);
            } catch (Exception ignored) {
            }
        }).start();
    }

    /**
     * sends a packet through redis
     *
     * @param packet the packet to get sent
     */

    public void sendPacket(Packet packet) {
        packet.onSend();
        new Thread(() ->
                runCommand(redis -> {
                    redis.publish(channel, packet.getClass().getName() + "//" + gson.toJson(packet));
        })).start();
    }

    /**
     * sends a packet through redis
     *
     * @param consumer the callback to be executed
     */
    private void runCommand(Consumer<Jedis> consumer) {
        Jedis jedis = jedisPool.getResource();
        if (jedis != null) {
            consumer.accept(jedis);
            jedisPool.returnResource(jedis);
        }
    }
}
