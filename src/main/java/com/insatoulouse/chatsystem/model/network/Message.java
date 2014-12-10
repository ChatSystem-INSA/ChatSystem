package com.insatoulouse.chatsystem.model.network;

/**
 * Message class
 * Send/receive message to/from network
 * {
 *     "type":"message",
 *     "messageNumber":3,
 *     "messageData":"Mon message"
 * }
 */
public class Message extends Packet {

    private int messageNumber;
    private String messageData;

    public Message(int messageNumber, String messageData) {
        super(Packet.TYPE_MESSAGE);
        setMessageNumber(messageNumber);
        setMessageData(messageData);
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
