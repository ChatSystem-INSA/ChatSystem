package com.insatoulouse.chatsystem.model.network;

/**
 * Abstract class Packet
 * Exchange network signal
 */
abstract public class Packet {

    public static final String TYPE_HELLO = "hello";
    public static final String TYPE_MESSAGE = "message";
    public static final String TYPE_MESSAGE_ACK = "messageAck";
    public static final String TYPE_HELLO_ACK = "helloAck";
    public static final String TYPE_GOODBYE = "goodBye";

    public static final String FIELD_USERNAME = "userName";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_MESSAGE_NUMBER = "messageNumber";
    public static final String FIELD_MESSAGE_DATA = "messageData";

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
