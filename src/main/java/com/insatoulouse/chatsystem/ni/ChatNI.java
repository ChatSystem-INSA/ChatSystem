package com.insatoulouse.chatsystem.ni;

import com.insatoulouse.chatsystem.Controller;
import com.insatoulouse.chatsystem.exception.LogicalException;
import com.insatoulouse.chatsystem.exception.PacketException;
import com.insatoulouse.chatsystem.exception.TechnicalException;
import com.insatoulouse.chatsystem.model.*;
import com.insatoulouse.chatsystem.model.network.*;
import com.insatoulouse.chatsystem.model.network.dao.AbstractFactory;
import com.insatoulouse.chatsystem.model.network.dao.PacketParser;
import com.insatoulouse.chatsystem.utils.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Created by tlk on 27/11/14.
 */
public class ChatNI {

    private static int message_id = 0;
    private Controller controller;
    private TcpListener tcpListener;
    private UdpListener udpListener;
    private NetworkInvoker invoker;
    private InetAddress broadcastAddr;

    private static final Logger l = LogManager.getLogger(ChatNI.class.getName());

    public ChatNI(Controller c) {
        this.controller = c;
    }

    public void start(InetAddress addr) throws TechnicalException
    {
        this.broadcastAddr = addr;
        this.tcpListener = new TcpListener();
        this.udpListener = new UdpListener(this);
        this.udpListener.start();
        this.invoker = new NetworkInvoker();
        this.invoker.start();
    }

    public void exit() {
        if(tcpListener != null)
            tcpListener.close();
        if(udpListener != null)
            udpListener.close();
        if(udpListener != null)
            invoker.close();
    }

    public ArrayList<InetAddress> getNetworkBroadcastAddresses() throws TechnicalException
    {
        ArrayList<InetAddress> ret = new ArrayList<InetAddress>();
        try {
            Enumeration list;
            list = NetworkInterface.getNetworkInterfaces();

            while(list.hasMoreElements())
            {
                NetworkInterface iface = (NetworkInterface) list.nextElement();
                if(iface == null)
                {
                    continue;
                }

                if(!iface.isLoopback() && iface.isUp())
                {
                    Iterator it = iface.getInterfaceAddresses().iterator();
                    while(it.hasNext())
                    {
                        InterfaceAddress address = (InterfaceAddress) it.next();
                        if(address == null)
                        {
                            continue;
                        }

                        InetAddress broadcast = (InetAddress) address.getBroadcast();
                        if(broadcast != null)
                        {
                            ret.add(broadcast);
                        }

                    }
                }
            }

        } catch (SocketException e) {
            throw new TechnicalException("Impossible de récuperer la liste des interfaces");
        }
        return ret;
    }

    public void processPacket(DatagramPacket packet) {

        PacketParser parser = AbstractFactory.getFactory(AbstractFactory.Type.JSON).getPacketParser();
        String data = new String(packet.getData(), packet.getOffset(), packet.getLength());

        l.debug("processing incomming packet : " + data);

        try {
            Packet message = parser.read(data);
            if(message instanceof Hello)
            {
                this.controller.processHello((Hello) message, packet.getAddress());
            } else if(message instanceof HelloAck)
            {
                this.controller.processHelloAck((HelloAck) message, packet.getAddress());
            }else if(message instanceof Goodbye)
            {
                this.controller.processGoodBye(packet.getAddress());
            } else if(message instanceof Message)
            {
                this.controller.processMessage((Message) message, packet.getAddress());
            } else if(message instanceof MessageAck)
            {
                this.controller.processMessageAck((MessageAck) message, packet.getAddress());
            }
        } catch (PacketException e) {
            l.debug("message JSON non valide : "+data,e);
        }

    }

    public void sendHello(User u) throws TechnicalException
    {
        Packet p;
        try {
            p = new Hello(u.getName());
            sendBroadcast(p);
        } catch (LogicalException e) {}
    }

    public void sendHelloAck(User from, User to) throws TechnicalException
    {
        try {
            Packet p = new HelloAck(from.getName());
            sendUnicast(p, to.getIp());
        } catch (LogicalException e) {}
    }

    public void sendGoodbye() throws TechnicalException
    {
        Packet p = new Goodbye();
        sendBroadcast(p);
    }

    public void sendMessage(User u, String message) throws TechnicalException
    {
        Packet p = null;
        try {
            p = new Message(ChatNI.message_id, message);
            ChatNI.message_id += 1;
            sendUnicast(p, u.getIp());
        } catch (LogicalException e) {
        }
    }

    public void sendMessageAck(User u, int message_id) throws  TechnicalException
    {
        try {
            Packet p = new MessageAck(message_id);
            sendUnicast(p, u.getIp());
        } catch (LogicalException e) {}
    }

    private void sendUnicast(Packet p, InetAddress addr) throws TechnicalException
    {
        PacketParser parser = AbstractFactory.getFactory(AbstractFactory.Type.JSON).getPacketParser();
        String data = parser.write(p);
        if(data != null)
        {
            DatagramPacket dp = new DatagramPacket(data.getBytes(), data.length());
            dp.setAddress(addr);
            dp.setPort(Integer.parseInt(Config.getInstance().getProperties(Config.CONFIG_PORT)));
            UdpSenderCommand cmd = new UdpSenderCommand(dp);
            this.invoker.addCommand(cmd);
        } else {
            l.error("Impossible de générer le JSON : " + p);
        }
    }

    private void sendBroadcast(Packet p) throws TechnicalException
    {
        this.sendUnicast(p, this.broadcastAddr);
    }

}
