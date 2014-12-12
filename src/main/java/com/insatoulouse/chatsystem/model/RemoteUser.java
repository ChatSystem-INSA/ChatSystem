package com.insatoulouse.chatsystem.model;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 07/12/14.
 */
public class RemoteUser extends User {

    private ArrayList<MessageNetwork> messages = new ArrayList<MessageNetwork>();

    private Boolean isConnected = true;

    public RemoteUser( String name, InetAddress ip) {
        super(false, name, ip);
    }

    public void addMessage(MessageNetwork message){
        messages.add(message);
    }

    public List<MessageNetwork> getMessages() {
        return messages;
    }

    public MessageNetwork getLastMessage() {
        if(!messages.isEmpty()){
            return messages.get(messages.size() -1);
        }
        else
            return null;
    }

    public Boolean isConnected() {
        return isConnected;
    }

    public void setIsConnected(Boolean isConnected) {
        this.isConnected = isConnected;
    }
}
