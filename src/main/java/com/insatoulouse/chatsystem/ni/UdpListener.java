package com.insatoulouse.chatsystem.ni;

import com.insatoulouse.chatsystem.exception.TechnicalException;
import com.insatoulouse.chatsystem.utils.Config;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;


/**
 * Created by tlk on 27/11/14.
 */
public class UdpListener extends Thread {

    private ChatNI chatNI;
    private UdpSocket socket;

    public UdpListener(ChatNI chatNI) throws IOException, TechnicalException {
        this.chatNI = chatNI;
        this.socket = new UdpSocket(Integer.parseInt(Config.getInstance().getProperties(Config.CONFIG_PORT)));
        this.socket.joinGroup(Inet4Address.getByName(Config.getInstance().getProperties(Config.CONFIG_ADDRESS)));
    }

    public void run()
    {

        byte buffer[] = new byte[4096];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while(true){
            try {
                this.socket.receive(packet);
                chatNI.processPacket(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
