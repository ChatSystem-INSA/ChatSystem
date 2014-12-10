package com.insatoulouse.chatsystem.model.network;

import com.insatoulouse.chatsystem.exception.LogicalException;

/**
 * Hello class
 * Signal send/receive to/from network when a User connect to ChatSystem
 * {
 *      "type":"hello",
 *      "userName":"test"
 * }
 */
public class Hello extends Packet {

    private String userName;

    public Hello(String data) throws LogicalException {
        super(Packet.TYPE_HELLO);
        setUserName(data);
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
