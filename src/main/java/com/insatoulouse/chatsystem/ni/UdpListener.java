package com.insatoulouse.chatsystem.ni;

import java.net.SocketException;

/**
 * Created by tlk on 27/11/14.
 */
public class UdpListener {

    private UdpSocket socket;

    public UdpListener() throws SocketException {
        this.socket = UdpSocket.getInstance();
    }

}
