package com.insatoulouse.chatsystem.model;

/**
 * Created by Banana on 28/11/14.
 */
public class Packet {

    public static final String TYPE_HELLO = "hello";
    public static final String TYPE_MESSAGE = "message";

    private String type;

    public Packet(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
