package com.insatoulouse.chatsystem.model;


public class JsonFactory extends AbstractFactory {
    private static JsonFactory instance = null;

    private JsonFactory() {} // Pattern Singleton

    public static synchronized JsonFactory getInstance() {
        if(instance == null) instance = new JsonFactory();
        return instance;
    }

    public PacketParser getMessageParser() {
        return new PacketParserJson();
    }
}