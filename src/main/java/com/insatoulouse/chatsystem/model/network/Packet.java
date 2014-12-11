package com.insatoulouse.chatsystem.model.network;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * interface Packet
 * Exchange network signal
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Hello.class, name = "hello"),
        @JsonSubTypes.Type(value = HelloAck.class, name = "helloAck"),
        @JsonSubTypes.Type(value = Message.class, name = "message"),
        @JsonSubTypes.Type(value = MessageAck.class, name = "messageAck"),
        @JsonSubTypes.Type(value = Goodbye.class, name = "goodBye") })
public interface Packet {}
