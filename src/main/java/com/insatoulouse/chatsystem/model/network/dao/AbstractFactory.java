package com.insatoulouse.chatsystem.model.network.dao;

public abstract class AbstractFactory {

    public enum Type {JSON}

    public abstract PacketParser getPacketParser();

    public static AbstractFactory getFactory(Type choice) {
        switch (choice) {
            case JSON:
                return JsonFactory.getInstance();
            default:
                return null;
        }
    }
}