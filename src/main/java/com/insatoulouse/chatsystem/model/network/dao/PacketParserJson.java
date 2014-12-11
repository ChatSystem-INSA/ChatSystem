package com.insatoulouse.chatsystem.model.network.dao;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insatoulouse.chatsystem.exception.PacketException;
import com.insatoulouse.chatsystem.model.network.*;

import java.io.IOException;

/**
 * PacketParserJson class
 *
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
            throw new PacketException("Impossible de mettre le json en Packet",e);
        } catch (JsonParseException e) {
            throw new PacketException("Impossible de mettre le json en Packet",e);
        } catch (IOException e) {
            throw new PacketException("Impossible de mettre le json en Packet",e);
        }
    }

    @Override
    public String write(Packet p) throws PacketException {
        String ret;
        try {
            ret = mapper.writeValueAsString(p);
        } catch (JsonProcessingException e) {
            throw new PacketException("Impossible de mettre le Packet en json",e);
        }
        return ret;
    }

}