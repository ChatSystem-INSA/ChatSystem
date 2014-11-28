package com.insatoulouse.chatsystem.ni;

import java.io.IOException;

/**
 * Created by tlk on 27/11/14.
 */
public class TcpListener extends Thread {

    private TcpSocket socket;
    private Boolean isRunning;

    public TcpListener() throws IOException {
        this.socket = TcpSocket.getInstance();
    }

    public void run()
    {
        while(isRunning){}
    }


    public synchronized void close() {
        isRunning = false;
        this.interrupt();
        try {
            this.socket.close();
        } catch (IOException ignored) {}
    }
}
