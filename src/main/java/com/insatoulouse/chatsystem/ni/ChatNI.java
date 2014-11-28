package com.insatoulouse.chatsystem.ni;

import com.insatoulouse.chatsystem.Controller;
import com.insatoulouse.chatsystem.exception.PacketException;
import com.insatoulouse.chatsystem.exception.TechnicalException;
import com.insatoulouse.chatsystem.model.*;
import com.insatoulouse.chatsystem.utils.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by tlk on 27/11/14.
 */
public class ChatNI {

    private Controller controller;
    private TcpListener tcpListener;
    private UdpListener udpListener;
    private NetworkInvoker invoker;

    private static final Logger l = LogManager.getLogger(ChatNI.class.getName());

    public ChatNI(Controller c) throws TechnicalException {
        this.controller = c;
        try {
            this.tcpListener = new TcpListener();
            this.udpListener = new UdpListener(this);
            this.udpListener.start();
            this.invoker = new NetworkInvoker();
            this.invoker.start();
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

    public void sendBroadcast(Packet p) throws TechnicalException
    {
        PacketParser parser = AbstractFactory.getFactory(AbstractFactory.Type.JSON).getPacketParser();
        try {
            String data = parser.write(p);
            if(data != null)
            {
                UdpSenderCommand cmd = new UdpSenderCommand(data, InetAddress.getByName(Config.getInstance().getProperties(Config.CONFIG_ADDRESS)));
                this.invoker.addCommand(cmd);
            } else {
                l.error("impossible de lancer le pacjet ehn briad");
            }

        } catch (PacketException e) {
            l.error("sendBroadcast : "+p, e);
        } catch (UnknownHostException e) {
            l.error("impossible' de toper l'adresse de broadcast");
        }
    }

}
