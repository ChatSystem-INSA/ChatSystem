package com.insatoulouse.chatsystem.model.network;

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
public class HelloAck extends Packet {

    private String userName;

    public HelloAck(String userName) throws LogicalException {
        super(Packet.TYPE_HELLO_ACK);
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
