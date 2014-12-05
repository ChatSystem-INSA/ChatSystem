package com.insatoulouse.chatsystem.model;

/**
 * Created by tlk on 05/12/14.
 */
public class Message extends Packet {

    private int messageNumber;
    private String messageData;

    public Message(int messageNumber, String messageData) {
        super(Packet.TYPE_MESSAGE);
        this.messageNumber = messageNumber;
        this.messageData = messageData;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getMessageData() {
        return messageData;
    }

    public void setMessageData(String messageData) {
        this.messageData = messageData;
    }
}
