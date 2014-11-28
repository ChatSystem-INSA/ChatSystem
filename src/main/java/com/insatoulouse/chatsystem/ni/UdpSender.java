package com.insatoulouse.chatsystem.ni;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;

public class UdpSender {

    private static final Logger l = LogManager.getLogger(ChatNI.class.getName());

    private String data;
    private InetAddress addr;

    public UdpSender(String data, InetAddress addr) {
        this.data = data;
        this.addr = addr;
    }

    public void send()
    {
        l.debug("jenvipoe" + this.data);
    }

}
