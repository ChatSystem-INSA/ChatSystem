package com.insatoulouse.chatsystem.ni;

import com.insatoulouse.chatsystem.exception.TechnicalException;
import com.insatoulouse.chatsystem.utils.Config;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;


public class UdpListener extends Thread {

    private ChatNI chatNI;
    private UdpSocket socket;
    private Boolean isRunning = true;

    public UdpListener(ChatNI chatNI) throws IOException, TechnicalException {
        this.chatNI = chatNI;
        this.socket = new UdpSocket(Integer.parseInt(Config.getInstance().getProperties(Config.CONFIG_PORT)));
    }

    public void run()
    {

        byte buffer[] = new byte[4096];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while(isRunning){
            try {
                this.socket.receive(packet);
                chatNI.processPacket(packet);
            } catch (IOException ignored) {}
        }
    }

    public synchronized void close() {
        isRunning = false;
        this.socket.close();
        this.interrupt();
    }

}
