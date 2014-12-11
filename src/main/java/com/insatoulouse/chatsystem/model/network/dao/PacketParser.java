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

package com.insatoulouse.chatsystem.model.network.dao;

import com.insatoulouse.chatsystem.exception.PacketException;
import com.insatoulouse.chatsystem.model.network.Packet;

/**
 * PacketParser interface
 */
public interface PacketParser {

    /**
     * Get a Packet from a String data input
     *
     * @param data String input
     * @return corresponding Packet
     * @throws PacketException
     */
    public Packet read(String data) throws PacketException;

    /**
     * Get string from Packet
     *
     * @param data Packet to serialize
     * @return corresponding string
     * @throws PacketException
     */
    public String write(Packet data) throws PacketException;
}
