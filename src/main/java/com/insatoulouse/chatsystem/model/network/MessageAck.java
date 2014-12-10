package com.insatoulouse.chatsystem.model.network;

/**
 * MessageAck class
 * Send/Receive ack message
 * {
 *     "type":"messageAck",
 *     "messageNumber":3
 * }
 */
public class MessageAck extends Packet {

    private int messageNumber;

    public MessageAck(int messageNumber) {
        super(Packet.TYPE_MESSAGE_ACK);
        setMessageNumber(messageNumber);
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }
}
