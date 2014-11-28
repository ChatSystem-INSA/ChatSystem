package com.insatoulouse.chatsystem.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insatoulouse.chatsystem.exception.PacketException;

import java.io.IOException;
import java.io.OutputStream;

public class MessageParserJson implements MessageParser {
    private ObjectMapper mapper = new ObjectMapper();

    public Message read(String data) throws PacketException {
        Message ret = null;
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

        try {
            if(type.equals(Hello.type))
            {
                ret = mapper.readValue(data, Hello.class);
            } else {
                throw new PacketException("Impossible de parser le JSON - attribut 'type' invalide");
            }
        } catch (IOException e) {
            throw new PacketException("Impossible de parser le JSON - message invalide");
        }

        return ret;
    }

    @Override
    public void write(OutputStream out, Message data)throws IOException {
        mapper.writeValue(out, data);
    }

}