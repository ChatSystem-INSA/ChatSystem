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

        if(root.get(Packet.FIELD_TYPE) == null)
        {
            throw new PacketException("Le message JSON ne contient pas d'attribut 'type'");
        }

        String type = root.get(Packet.FIELD_TYPE).asText();

        if(type.equals(Packet.TYPE_HELLO)) {
            if (root.get(Packet.FIELD_USERNAME) != null) {
                ret = new Hello(root.get(Packet.FIELD_USERNAME).asText());
            } else {
                throw new PacketException("Message hello invalide : manque le username");
            }
        } else if (type.equals(Packet.TYPE_HELLO_ACK)) {
            if(root.get(Packet.FIELD_USERNAME) != null)
            {
                ret = new HelloAck(root.get(Packet.FIELD_USERNAME).asText());
            } else {
                throw new PacketException("Message helloAck invalide : manque le username");
            }
        } else if (type.equals(Packet.TYPE_GOODBYE)) {
            ret = new Goodbye();
        } else if(type.equals(Packet.TYPE_MESSAGE)) {

            if (root.get(Packet.FIELD_MESSAGE_NUMBER) != null && root.get(Packet.FIELD_MESSAGE_DATA) != null) {
                ret = new Message(root.get(Packet.FIELD_MESSAGE_NUMBER).asInt(), root.get(Packet.FIELD_MESSAGE_DATA).asText());
            } else {
                throw new PacketException("Message message invalide.");
            }
        } else if(type.equals(Packet.TYPE_MESSAGE_ACK)) {
            if(root.get(Packet.FIELD_MESSAGE_NUMBER) != null)
            {
                ret = new MessageAck(root.get(Packet.FIELD_MESSAGE_NUMBER).asInt());
            } else {
                throw new PacketException("Message messageAck invalide");
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