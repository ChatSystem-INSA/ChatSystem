package com.insatoulouse.chatsystem.model;

import java.net.InetAddress;

/**
 * Created by david on 27/11/14.
 */
public class User {

    private boolean isLocalUser;
    private String name;
    private InetAddress ip;

    public User(boolean isLocalUser, String name) {
        this.isLocalUser = isLocalUser;
        this.name = name;
    }

    public void setLocalUser(boolean v)
    {
        this.isLocalUser = v;
    }

    public boolean isLocalUser()
    {
        return this.isLocalUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
