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

package com.insatoulouse.chatsystem.model.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insatoulouse.chatsystem.exception.LogicalException;

/**
 * HelloAck class
 * Signal is sent to new user
 * Signal is received from other user when send Hello
 * {
 * "type":"helloAck",
 * "userName":"test"
 * }
 *
 * @see Hello
 */
public class HelloAck implements Packet {

    /**
     * userName
     * Must be not null and empty
     */
    private String userName;

    public HelloAck(@JsonProperty(value = "userName", required = true) String userName) throws LogicalException {
        setUserName(userName);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) throws LogicalException {
        if (userName == null || userName.isEmpty()) {
            throw new LogicalException("Bad userName");
        }
        this.userName = userName;
    }
}
