package com.insatoulouse.chatsystem.ni;

import com.insatoulouse.chatsystem.Controller;
import com.insatoulouse.chatsystem.exception.PacketException;
import com.insatoulouse.chatsystem.model.AbstractFactory;
import com.insatoulouse.chatsystem.model.Message;
import com.insatoulouse.chatsystem.model.MessageParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

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


    public void processPacket(String s) {
        MessageParser parser = AbstractFactory.getFactory(AbstractFactory.Type.JSON).getMessageParser();
        try {
            Message message = parser.read(s);
        } catch (PacketException e) {
            l.debug("message JSON non valide : "+s,e);
        }
    }
}
