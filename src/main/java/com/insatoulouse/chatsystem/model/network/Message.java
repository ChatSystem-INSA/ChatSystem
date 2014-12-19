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
 * Message class
 * Send/receive message to/from network
 * {
 * "type":"message",
 * "messageNumber":3,
 * "messageData":"Mon message"
 * }
 */
public class Message implements Packet {

    private static int countMessage = 0;

    /**
     * messageNumber
     * Must be not null and greater than 0
     */
    private Integer messageNumber;

    /**
     * messageData
     * Must be not null and empty
     */
    private String messageData;

    public Message(@JsonProperty(value = "messageNumber", required = true) Integer messageNumber, @JsonProperty(value = "messageData", required = true) String messageData) throws LogicalException {
        setMessageNumber(messageNumber);
        setMessageData(messageData);
        Message.countMessage++;
    }

    public static int getCountMessage() {
        return countMessage;
    }

    public Integer getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(Integer messageNumber) throws LogicalException {
        if (messageNumber == null || messageNumber < 0) {
            throw new LogicalException("Bad message number");
        }
        this.messageNumber = messageNumber;
    }

    public String getMessageData() {
        return messageData;
    }

    public void setMessageData(String messageData) throws LogicalException {
        if (messageData == null || messageData.isEmpty()) {
            throw new LogicalException("Bad message data");
        }
        this.messageData = messageData;
    }
}
