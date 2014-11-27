package com.insatoulouse.chatsystem.ni;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by tlk on 27/11/14.
 */
public class TCPSocket extends ServerSocket {

    private static TCPSocket instance;

    private TCPSocket() throws IOException {
    }

    public static synchronized TCPSocket getInstance() throws IOException
    {
        if(instance == null)
        {
            instance = new TCPSocket();
        }
        return instance;
    }

}
