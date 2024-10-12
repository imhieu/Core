package me.hieu.core.redis.packet;

public abstract class Packet {

    public abstract void onReceived();
    public abstract void onSend();

}
