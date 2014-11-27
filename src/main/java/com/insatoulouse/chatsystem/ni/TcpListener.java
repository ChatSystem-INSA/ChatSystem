package com.insatoulouse.chatsystem.ni;

import java.io.IOException;

/**
 * Created by tlk on 27/11/14.
 */
public class TcpListener {

    private TcpSocket socket;

    public TcpListener() throws IOException {
        this.socket = TcpSocket.getInstance();
    }

}
