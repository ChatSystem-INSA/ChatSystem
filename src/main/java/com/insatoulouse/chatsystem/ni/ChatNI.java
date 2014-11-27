package com.insatoulouse.chatsystem.ni;

import java.io.IOException;
import java.net.SocketException;

/**
 * Created by tlk on 27/11/14.
 */
public class ChatNI {

    private int port;
    private TcpListener tcpListener;
    private UdpListener udpListener;
    private NetworkInvoker invoker;

    public ChatNI(int port) throws IOException {
        this.port = port;
        this.tcpListener = new TcpListener();
        this.udpListener = new UdpListener();
        this.invoker = new NetworkInvoker();
    }

}
