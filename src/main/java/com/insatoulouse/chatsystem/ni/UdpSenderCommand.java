package com.insatoulouse.chatsystem.ni;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;

public class UdpSenderCommand implements NetworkCommand {

    private static final Logger l = LogManager.getLogger(UdpSenderCommand.class.getName());

    private UdpSender udpSender;

    public UdpSenderCommand(String data, InetAddress addr) {
        this.udpSender = new UdpSender(data, addr);
    }

    @Override
    public void execute() {
        this.udpSender.send();
    }
}
