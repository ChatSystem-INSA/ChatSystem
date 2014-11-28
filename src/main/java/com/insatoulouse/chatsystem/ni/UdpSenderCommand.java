package com.insatoulouse.chatsystem.ni;

import java.net.InetAddress;

public class UdpSenderCommand implements NetworkCommand {

    private UdpSender udpSender;

    public UdpSenderCommand(String data, InetAddress addr) {
        this.udpSender = new UdpSender(data, addr);
    }

    @Override
    public void execute() {
        this.udpSender.send();
    }
}
