package com.insatoulouse.chatsystem.ni;

import com.insatoulouse.chatsystem.exception.TechnicalException;

import java.io.IOException;

/**
 * Created by tlk on 27/11/14.
 */
public class TcpListener extends Thread {

    private TcpSocket socket;
    private Boolean isRunning;

    public TcpListener() throws TechnicalException {
        try {
            this.socket = TcpSocket.getInstance();
        } catch (IOException e) {
            throw new TechnicalException("Impossible de démarrer le TCPListener : " + e.getMessage());
        }
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
