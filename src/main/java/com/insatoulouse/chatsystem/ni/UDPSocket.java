package com.insatoulouse.chatsystem.ni;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by tlk on 27/11/14.
 */
public class UDPSocket extends DatagramSocket {

    private static UDPSocket instance = null;

    private UDPSocket() throws SocketException {
    }

    public static synchronized UDPSocket getInstance() throws SocketException
    {
        if(instance == null) {
             instance = new UDPSocket();
        }
        return instance;
    }

}
