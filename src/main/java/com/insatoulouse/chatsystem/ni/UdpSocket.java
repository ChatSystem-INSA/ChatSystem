package com.insatoulouse.chatsystem.ni;

import java.io.IOException;
import java.net.MulticastSocket;
import java.net.SocketException;

/**
 * Created by tlk on 27/11/14.
 */
public class UdpSocket extends MulticastSocket {


    public UdpSocket(int port) throws IOException {
        super(port);
    }
}
