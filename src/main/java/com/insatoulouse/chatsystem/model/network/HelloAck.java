package com.insatoulouse.chatsystem.model.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insatoulouse.chatsystem.exception.LogicalException;

/**
 * HelloAck class
 * Signal is sent to new user
 * Signal is received from other user when send Hello
 * {
 *      "type":"helloAck",
 *      "userName":"test"
 * }
 * @see Hello
 */
public class HelloAck implements Packet {

    private String userName;

    public HelloAck(@JsonProperty(value = "userName", required = true) String userName) throws LogicalException {
        setUserName(userName);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) throws LogicalException {
        if(userName == null || userName.isEmpty()){
            throw new LogicalException("Bad userName");
        }
        this.userName = userName;
    }
}
