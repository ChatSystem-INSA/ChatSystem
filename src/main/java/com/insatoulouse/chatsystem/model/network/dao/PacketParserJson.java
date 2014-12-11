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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insatoulouse.chatsystem.exception.PacketException;
import com.insatoulouse.chatsystem.model.network.Packet;

import java.io.IOException;

/**
 * PacketParserJson class
 * <p/>
 * Use jackson lib to parse a packet in JSON
 *
 * @see com.insatoulouse.chatsystem.model.network.Packet
 * @see com.fasterxml.jackson.databind.ObjectMapper
 * @see com.insatoulouse.chatsystem.model.network.dao.PacketParser
 */
public class PacketParserJson implements PacketParser {

    /**
     * ObjectMapper is useful to serialize (and deserialize) an Packet in JSON
     */
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Packet read(String data) throws PacketException {

        try {
            return mapper.readValue(data, Packet.class);
        } catch (JsonMappingException e) {
            throw new PacketException("Impossible de mettre le json en Packet", e);
        } catch (JsonParseException e) {
            throw new PacketException("Impossible de mettre le json en Packet", e);
        } catch (IOException e) {
            throw new PacketException("Impossible de mettre le json en Packet", e);
        }
    }

    @Override
    public String write(Packet p) throws PacketException {
        String ret;
        try {
            ret = mapper.writeValueAsString(p);
        } catch (JsonProcessingException e) {
            throw new PacketException("Impossible de mettre le Packet en json", e);
        }
        return ret;
    }

}