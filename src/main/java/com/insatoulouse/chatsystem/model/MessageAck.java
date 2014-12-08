package com.insatoulouse.chatsystem.model;

/**
 * Created by rusty on 08/12/2014.
 */
public class MessageAck extends Packet {

    private int messageNumber;

    public MessageAck(int messageNumber) {
        super(Packet.TYPE_MESSAGE_ACK);
        this.messageNumber = messageNumber;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }
}
