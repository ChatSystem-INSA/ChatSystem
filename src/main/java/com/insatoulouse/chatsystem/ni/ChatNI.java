package com.insatoulouse.chatsystem.ni;

/**
 * Created by tlk on 27/11/14.
 */
public class ChatNI {

    private static ChatNI instance;
    private TCPListener tcpListener;
    private UDPListener udpListener;
    private NetworkInvoker invoker;

    private ChatNI()
    {
        this.tcpListener = new TCPListener();
        this.udpListener = new UDPListener();
        this.invoker = new NetworkInvoker();
    }

    public static synchronized ChatNI getInstance()
    {
        if(instance == null)
        {
            instance = new ChatNI();
        }
        return instance;
    }

}
