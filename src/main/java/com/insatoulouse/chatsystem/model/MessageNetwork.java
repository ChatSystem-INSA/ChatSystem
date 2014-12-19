/*
 * Chat System - P2P
 *     Copyright (C) 2014 LIVET BOUTOILLE
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.insatoulouse.chatsystem.model;

/**
 * MessageNetwork class
 * Represent a message from/to user
 */
public class MessageNetwork {

    /**
     * Message from network
     */
    public static final int IN = 1;

    /**
     * Message to network
     */
    public static final int OUT = 2;

    /**
     * Type IN/OUT
     */
    private int type;

    /**
     * Remote user
     */
    private RemoteUser user;

    /**
     * Message
     */
    private String message;

    public MessageNetwork(RemoteUser u, String message) {
        this(IN, u, message);
    }

    public MessageNetwork(int type, RemoteUser u, String message) {
        this.type = type;
        this.user = u;
        u.addMessage(this);
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(RemoteUser user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MessageNetwork{" +
                "message='" + message + '\'' +
                '}';
    }
}
