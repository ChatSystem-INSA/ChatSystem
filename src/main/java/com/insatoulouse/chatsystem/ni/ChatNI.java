package com.insatoulouse.chatsystem.ni;

import com.insatoulouse.chatsystem.Controller;
import com.insatoulouse.chatsystem.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

/**
 * Created by tlk on 27/11/14.
 */
public class ChatNI {

    private Controller controller;
    private int port;
    private TcpListener tcpListener;
    private UdpListener udpListener;
    private NetworkInvoker invoker;

    private static final Logger l = LogManager.getLogger(ChatNI.class.getName());

    public ChatNI(Controller c, int port) {
        this.controller = c;
        this.port = port;
        try {
            this.tcpListener = new TcpListener();
            this.udpListener = new UdpListener(this, port);
            this.udpListener.run();
            this.invoker = new NetworkInvoker();
        } catch (IOException e) {
            l.error("Network connection error",e);
        }
    }


    public void processPacket(DatagramPacket packet) {

        MessageParser parser = AbstractFactory.getFactory(AbstractFactory.Type.JSON).getMessageParser();
        String data = new String(packet.getData(), packet.getOffset(), packet.getLength());

        try {
            Message message = parser.read(data);
            if(message instanceof Hello)
            {
                this.controller.processHello((Hello) message, packet.getAddress());
            }
        } catch (MessageException e) {
            l.debug("message JSON non valide : "+data,e);
        }

    }
}
