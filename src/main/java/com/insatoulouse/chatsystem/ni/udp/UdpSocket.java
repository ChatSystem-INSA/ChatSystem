package com.insatoulouse.chatsystem.ni.udp;

import java.io.IOException;
import java.net.DatagramSocket;

/**
 * Created by tlk on 27/11/14.
 */
public class UdpSocket extends DatagramSocket {


    public UdpSocket(int port) throws IOException {
        super(port);
    }
}
