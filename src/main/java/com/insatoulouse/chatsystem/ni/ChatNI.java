/*
 * Chat System - P2P
 *     Copyright (C) 2014 LIVET BOUTOILLE
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.insatoulouse.chatsystem.ni;

import com.insatoulouse.chatsystem.Controller;
import com.insatoulouse.chatsystem.exception.LogicalException;
import com.insatoulouse.chatsystem.exception.PacketException;
import com.insatoulouse.chatsystem.exception.TechnicalException;
import com.insatoulouse.chatsystem.model.RemoteUser;
import com.insatoulouse.chatsystem.model.User;
import com.insatoulouse.chatsystem.model.network.*;
import com.insatoulouse.chatsystem.model.network.dao.AbstractFactory;
import com.insatoulouse.chatsystem.model.network.dao.PacketParser;
import com.insatoulouse.chatsystem.ni.tcp.TcpListener;
import com.insatoulouse.chatsystem.ni.tcp.TcpSenderCommand;
import com.insatoulouse.chatsystem.ni.udp.UdpListener;
import com.insatoulouse.chatsystem.ni.udp.UdpSenderCommand;
import com.insatoulouse.chatsystem.utils.NetworkTools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * ChatNI class
 */
public class ChatNI {

    private static final Logger l = LogManager.getLogger(ChatNI.class.getName());
    private final PacketParser parser = AbstractFactory.getFactory(AbstractFactory.Type.JSON).getPacketParser();
    private final Controller controller;
    private TcpListener tcpListener;
    private UdpListener udpListener;
    private NetworkInvoker invoker;
    private InetAddress broadcastAddr;

    public ChatNI(Controller c) {
        this.controller = c;
    }

    public void start(InetAddress addr) throws TechnicalException {
        l.trace("Start ChatNI");
        this.broadcastAddr = addr;

        this.tcpListener = new TcpListener(this);
        this.tcpListener.start();

        this.udpListener = new UdpListener(this);
        this.udpListener.start();

        this.invoker = new NetworkInvoker();
        this.invoker.start();
    }

    public void exit() {
        l.trace("Close ChatNI");
        if (tcpListener != null)
            tcpListener.close();
        if (udpListener != null)
            udpListener.close();
        if (udpListener != null)
            invoker.close();
    }

    public ArrayList<InetAddress> getNetworkBroadcastAddresses() throws TechnicalException {
        try {
            return NetworkTools.getBroadcastAddr();
        } catch (SocketException e) {
            l.error("Impossible de récuperer la liste des interfaces", e);
            throw new TechnicalException("Impossible de récuperer la liste des interfaces", e);
        }
    }

    /**
     * Execute controller method when received packet
     * Invalid packet are dropped
     *
     * @param packet incoming packet
     */
    public void processPacket(DatagramPacket packet) {

        String data = NetworkTools.getString(packet);
        l.debug("Processing incomming packet : " + data);

        try {
            Packet message = parser.read(data);
            if (message instanceof Hello) {
                this.controller.processHello(new RemoteUser(((Hello) message).getUserName(), packet.getAddress()));
            } else if (message instanceof HelloAck) {
                this.controller.processHelloAck(new RemoteUser(((HelloAck) message).getUserName(), packet.getAddress()));
            } else if (message instanceof Goodbye) {
                this.controller.processGoodBye(packet.getAddress());
            } else if (message instanceof Message) {
                this.controller.processMessage((Message) message, packet.getAddress());
            } else if (message instanceof MessageAck) {
                this.controller.processMessageAck((MessageAck) message, packet.getAddress());
            }
        } catch (PacketException e) {
            l.error("Drop invalid packet : " + data, e);
        }
    }

    /**
     * Process remote file
     * @param f remote file
     * @param addr remote entity
     */
    public void processFile(File f, InetAddress addr) {
        this.controller.processFile(f, addr);
    }

    /**
     * Send packet Hello to user u
     * @param u remote entity
     * @throws TechnicalException
     * @throws LogicalException
     */
    public void sendHello(User u) throws TechnicalException, LogicalException {
        Packet p = new Hello(u.getName());
        sendBroadcast(p);
    }

    /**
     * Send packet HelloAck to User to
     * @param from local entity
     * @param to remote entity
     * @throws TechnicalException
     * @throws LogicalException
     */
    public void sendHelloAck(User from, User to) throws TechnicalException, LogicalException {
        Packet p = new HelloAck(from.getName());
        sendUnicast(p, to.getIp());
    }

    /**
     * Send packet Goodbye on broadcast
     * @throws TechnicalException
     */
    public void sendGoodbye() throws TechnicalException {
        Packet p = new Goodbye();
        sendBroadcast(p);
    }

    /**
     * Send message to user u
     * @param u remote entity
     * @param message message to send
     * @throws TechnicalException
     * @throws LogicalException
     */
    public void sendMessage(User u, String message) throws TechnicalException, LogicalException {
        Packet p = new Message(Message.getCountMessage(), message);
        sendUnicast(p, u.getIp());
    }

    /**
     * send packet MessageAck to user u
     * @param u remote entity
     * @param messageId message id to ack
     * @throws TechnicalException
     * @throws LogicalException
     */
    public void sendMessageAck(User u, int messageId) throws TechnicalException, LogicalException {
        Packet p = new MessageAck(messageId);
        sendUnicast(p, u.getIp());
    }

    /**
     * send file f to user u
     * @param u remote entity
     * @param f local file to send
     */
    public void sendFile(User u, File f) {
        TcpSenderCommand cmd = new TcpSenderCommand(f, u.getIp());
        this.invoker.addCommand(cmd);
    }

    private void sendUnicast(Packet p, InetAddress addr) throws TechnicalException {
        l.trace("Send packet " + p + " to " + addr);
        String data = parser.write(p);
        if (data != null) {
            UdpSenderCommand cmd = new UdpSenderCommand(NetworkTools.getDatagramPacket(data, addr));
            this.invoker.addCommand(cmd);
        } else {
            l.error("Impossible de générer le JSON : " + p);
            throw new TechnicalException("Impossible de générer le JSON : " + p);
        }
    }

    private void sendBroadcast(Packet p) throws TechnicalException {
        this.sendUnicast(p, broadcastAddr);
    }

}
