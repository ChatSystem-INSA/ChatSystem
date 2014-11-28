package com.insatoulouse.chatsystem.model;

/**
 * Created by Banana on 28/11/14.
 */
public class Packet {

    public static final String TYPE_HELLO = "hello";
    public static final String TYPE_MESSAGE = "message";

    public static final String FIELD_USERNAME = "userName";
    public static final String FIELD_TYPE = "type";

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
