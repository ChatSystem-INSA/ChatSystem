package com.insatoulouse.chatsystem.exception;

public class PacketException extends TechnicalException {

    public PacketException(String str)
    {
        super(str);
    }

    public PacketException(String message, Throwable cause) {
        super(message, cause);
    }
}
