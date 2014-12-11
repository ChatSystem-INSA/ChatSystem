package com.insatoulouse.chatsystem.model.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insatoulouse.chatsystem.exception.LogicalException;

/**
 * MessageAck class
 * Send/Receive ack message
 * {
 *     "type":"messageAck",
 *     "messageNumber":3
 * }
 */
public class MessageAck implements Packet {

    private Integer messageNumber;

    public MessageAck(@JsonProperty(value = "messageNumber", required = true) Integer messageNumber) throws LogicalException {
        setMessageNumber(messageNumber);
    }

    public Integer getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(Integer messageNumber) throws LogicalException {
        if(messageNumber == null || messageNumber<0){
            throw new LogicalException("Bad message number");
        }
        this.messageNumber = messageNumber;
    }
}
