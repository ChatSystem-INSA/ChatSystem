package com.insatoulouse.chatsystem.ni;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by tlk on 27/11/14.
 */
public class TcpSocket extends ServerSocket {

    private static TcpSocket instance;

    private TcpSocket() throws IOException {
    }

    public static synchronized TcpSocket getInstance() throws IOException
    {
        if(instance == null)
        {
            instance = new TcpSocket();
        }
        return instance;
    }

}
