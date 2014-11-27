package com.insatoulouse.chatsystem.ni;

import com.insatoulouse.chatsystem.model.AbstractFactory;
import com.insatoulouse.chatsystem.model.Message;
import com.insatoulouse.chatsystem.model.MessageException;
import com.insatoulouse.chatsystem.model.MessageParser;

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
        this.udpListener = new UdpListener(this, port);
        this.udpListener.run();
        this.invoker = new NetworkInvoker();
    }


    public void processPacket(String s) {
        MessageParser parser = AbstractFactory.getFactory(AbstractFactory.Type.JSON).getMessageParser();
        try {
            Message message = parser.read(s);
        } catch (MessageException e) {
            System.out.println("ChatNI : message JSON non valide : " + s);
        }
    }
}
