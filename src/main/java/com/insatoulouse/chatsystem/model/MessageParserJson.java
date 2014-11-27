package com.insatoulouse.chatsystem.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;

public class MessageParserJson implements MessageParser {
    private ObjectMapper mapper = new ObjectMapper();

    public Message read(String data) throws MessageException {
        Message ret = null;
        JsonNode root = null;

        try {
            root = mapper.readTree(data);
        } catch (IOException e) {
            throw new MessageException("Impossible de parser le JSON");
        }

        if(root.get("type") == null)
        {
            throw new MessageException("Le message JSON ne contient pas d'attribut 'type'");
        }

        String type = root.get("type").asText();

        try {
            if(type.equals(Hello.type))
            {
                ret = mapper.readValue(data, Hello.class);
            } else {
                ret = mapper.readValue(data, Message.class);
            }
        } catch (IOException e) {
            throw new MessageException("Impossible de parser le JSON - message invalide");
        }

        return ret;
    }

    @Override
    public void write(OutputStream out, Message data)throws IOException {
        mapper.writeValue(out, data);
    }

}