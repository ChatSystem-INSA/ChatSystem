package com.insatoulouse.chatsystem.model.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insatoulouse.chatsystem.exception.LogicalException;

/**
 * Message class
 * Send/receive message to/from network
 * {
 *     "type":"message",
 *     "messageNumber":3,
 *     "messageData":"Mon message"
 * }
 */
public class Message implements Packet {

    private Integer messageNumber;
    private String messageData;

    public Message(@JsonProperty(value = "messageNumber", required = true) Integer messageNumber,@JsonProperty(value = "messageData", required = true)  String messageData) throws LogicalException {
        setMessageNumber(messageNumber);
        setMessageData(messageData);
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

    public String getMessageData() {
        return messageData;
    }

    public void setMessageData(String messageData) throws LogicalException {
        if(messageData == null || messageData.isEmpty()){
            throw new LogicalException("Bad message data");
        }
        this.messageData = messageData;
    }
}
