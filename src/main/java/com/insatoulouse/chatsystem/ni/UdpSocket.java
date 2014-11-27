package com.insatoulouse.chatsystem.ni;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by tlk on 27/11/14.
 */
public class UdpSocket extends DatagramSocket {

    private static UdpSocket instance = null;

    private UdpSocket() throws SocketException {
    }

    public static synchronized UdpSocket getInstance() throws SocketException
    {
        if(instance == null) {
             instance = new UdpSocket();
        }
        return instance;
    }

}
