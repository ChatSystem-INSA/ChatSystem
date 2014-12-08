package com.insatoulouse.chatsystem.ni;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpSender {

    private static final Logger l = LogManager.getLogger(UdpSender.class.getName());

    private DatagramPacket packet;

    public UdpSender(DatagramPacket packet) {
        this.packet = packet;
    }

    public void send()
    {
        try {
            DatagramSocket s = new DatagramSocket();
            s.send(this.packet);
            l.debug("Send : " + new String(this.packet.getData()) + " to " + this.packet.getAddress().toString()+":"+this.packet.getPort());
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
