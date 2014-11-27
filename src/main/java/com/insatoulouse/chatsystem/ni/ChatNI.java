package com.insatoulouse.chatsystem.ni;

/**
 * Created by tlk on 27/11/14.
 */
public class ChatNI {

    private TCPListener tcpListener;
    private UDPListener udpListener;
    private NetworkInvoker invoker;

    public ChatNI()
    {
        this.tcpListener = new TCPListener(this);
        this.udpListener = new UDPListener(this);
        this.invoker = new NetworkInvoker();
    }

}
