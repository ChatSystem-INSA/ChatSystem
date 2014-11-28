package com.insatoulouse.chatsystem.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insatoulouse.chatsystem.exception.PacketException;

import java.io.IOException;
import java.io.OutputStream;

public class PacketParserJson implements PacketParser {
    private ObjectMapper mapper = new ObjectMapper();

    public Packet read(String data) throws PacketException {
        Packet ret = null;
        JsonNode root = null;

        try {
            root = mapper.readTree(data);
        } catch (IOException e) {
            throw new PacketException("Impossible de parser le JSON");
        }

        if(root.get("type") == null)
        {
            throw new PacketException("Le message JSON ne contient pas d'attribut 'type'");
        }

        String type = root.get("type").asText();

        if(type.equals(Packet.TYPE_HELLO))
        {
            if(root.get("username") != null)
            {
                ret = new Hello(root.get("username").asText());
            } else {
                throw new PacketException("Message hello invalide : manque le suername");
            }

        } else {
            throw new PacketException("Impossible de parser le JSON - attribut 'type' invalide");
        }

        return ret;
    }

    @Override
    public String write(Packet p)throws PacketException {
        String ret = null;
        try {
            ret = mapper.writeValueAsString(p);
        } catch (JsonProcessingException e) {
            throw new PacketException("Impossible de mettre le Packet en json");
        }
        return ret;
    }

}