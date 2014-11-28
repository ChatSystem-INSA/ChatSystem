package com.insatoulouse.chatsystem.ni;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;


/**
 * Created by tlk on 27/11/14.
 */
public class UdpListener extends Thread {

    private ChatNI chatNI;
    private UdpSocket socket;

    public UdpListener(ChatNI chatNI, int port) throws IOException {
        this.chatNI = chatNI;
        this.socket = new UdpSocket(port);
        this.socket.joinGroup(Inet4Address.getByName("224.1.2.3"));
    }

    public void run()
    {

        byte buffer[] = new byte[4096];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while(true){
            try {
                this.socket.receive(packet);
                chatNI.processPacket(packet);
                // chatNI.processPacket(new String(packet.getData(), packet.getOffset(), packet.getLength()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
