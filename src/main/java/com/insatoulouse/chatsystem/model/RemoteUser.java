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

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * RemoteUser class
 * Represent a remote user (from network)
 *
 * @see com.insatoulouse.chatsystem.model.User
 */
public class RemoteUser extends User {

    /**
     * Historic of message
     */
    private final ArrayList<MessageNetwork> messages = new ArrayList<MessageNetwork>();

    public RemoteUser(String name, InetAddress ip) {
        super(name, ip);
    }

    public void addMessage(MessageNetwork message) {
        messages.add(message);
    }

    public List<MessageNetwork> getMessages() {
        return messages;
    }

    public MessageNetwork getLastMessage() {
        if (!messages.isEmpty()) {
            return messages.get(messages.size() - 1);
        } else
            return null;
    }
}
