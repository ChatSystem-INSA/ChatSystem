package com.insatoulouse.chatsystem.model;

public abstract class AbstractFactory {

    public enum Type {JSON}

    public abstract PacketParser getMessageParser();

    public static AbstractFactory getFactory(Type choice) {
        switch (choice) {
            case JSON:
                return JsonFactory.getInstance();
            default:
                return null;
        }
    }
}