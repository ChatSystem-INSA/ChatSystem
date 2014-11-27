package com.insatoulouse.chatsystem.ni;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by tlk on 27/11/14.
 */
public class UDPSocket extends DatagramSocket {

    private UDPSocket instance;

    private UDPSocket() throws SocketException {
    }

    public UDPSocket getInstance() throws SocketException
    {
        if(instance != null) {
            return this.instance;
        }
        return new UDPSocket();
    }

}
