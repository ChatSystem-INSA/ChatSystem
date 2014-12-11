package com.insatoulouse.chatsystem.model.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insatoulouse.chatsystem.exception.LogicalException;

/**
 * Hello class
 * Signal send/receive to/from network when a User connect to ChatSystem
 * {
 *      "type":"hello",
 *      "userName":"test"
 * }
 */
public class Hello implements Packet {

    private String userName;

    public Hello(@JsonProperty(value = "userName", required = true)  String data) throws LogicalException {
        setUserName(data);
    }

    public String getUserName() {
        return userName;
    }

    @JsonProperty(required = true)
    public void setUserName(String userName) throws LogicalException {
        if(userName == null || userName.isEmpty()){
            throw new LogicalException("Bad userName");
        }
        this.userName = userName;
    }

}
