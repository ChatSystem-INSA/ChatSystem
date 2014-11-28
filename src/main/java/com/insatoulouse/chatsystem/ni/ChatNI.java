package com.insatoulouse.chatsystem.ni;

import com.insatoulouse.chatsystem.Controller;
import com.insatoulouse.chatsystem.exception.PacketException;
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
            this.udpListener.start();
            this.invoker = new NetworkInvoker();
        } catch (IOException e) {
            l.error("Network connection error",e);
        }
    }

    public void processPacket(DatagramPacket packet) {

        PacketParser parser = AbstractFactory.getFactory(AbstractFactory.Type.JSON).getPacketParser();
        String data = new String(packet.getData(), packet.getOffset(), packet.getLength());

        try {
            Packet message = parser.read(data);
            if(message instanceof Hello)
            {
                this.controller.processHello((Hello) message, packet.getAddress());
            }
        } catch (PacketException e) {
            l.debug("message JSON non valide : "+data,e);
        }

    }

    public void sendBroadcast(Packet p)
    {
        PacketParser parser = AbstractFactory.getFactory(AbstractFactory.Type.JSON).getPacketParser();
        try {
            String data = parser.write(p);
        } catch (PacketException e) {
            l.error("sendBroadcast : "+p, e);
        }
    }

}
