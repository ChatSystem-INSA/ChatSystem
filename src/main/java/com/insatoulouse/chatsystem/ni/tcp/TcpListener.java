/*
 * Chat System - P2P
 *     Copyright (C) 2014 LIVET BOUTOILLE
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.insatoulouse.chatsystem.ni.tcp;

import com.insatoulouse.chatsystem.exception.TechnicalException;
import com.insatoulouse.chatsystem.ni.ChatNI;
import com.insatoulouse.chatsystem.utils.Config;
import com.insatoulouse.chatsystem.utils.FileTools;
import com.insatoulouse.chatsystem.utils.NetworkTools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by tlk on 27/11/14.
 */
public class TcpListener extends Thread {

    private static final Logger l = LogManager.getLogger(TcpListener.class.getName());

    private ChatNI chatNI;
    private TcpSocket socket;
    private Boolean isRunning = true;

    public TcpListener(ChatNI ni) throws TechnicalException {
        l.trace("Create TCPListener");
        this.chatNI = ni;
        try {
            this.socket = new TcpSocket(Integer.parseInt(Config.getInstance().getProperties(Config.CONFIG_PORT)));
        } catch (IOException e) {
            throw new TechnicalException("Impossible de démarrer le TCPListener : " + e.getMessage());
        }
    }

    public void run()
    {
        l.trace("Start TCPListener");
        while(isRunning){
            Socket s = null;
            try {
                if((s = this.socket.accept()) != null)
                {
                    l.trace("receive new file");
                    DataInputStream clientData = NetworkTools.getDataInputStreamFromSocket(s);
                    String filename = clientData.readUTF();
                    long size = clientData.readLong();

                    filename = FileTools.getTempFilename(filename);
                    OutputStream output = FileTools.getTempOutputStream(filename);

                    l.debug("filename = "+filename);
                    l.debug("size = " + size);
                    int bytesRead = 0;
                    byte[] buffer = new byte[1024];
                    while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                        output.write(buffer, 0, bytesRead);
                        size -= bytesRead;
                    }
                    s.close();
                    this.chatNI.processFile(new File(filename), s.getInetAddress());
                }
            } catch (IOException e) {
                l.warn(e);
            }
        }
    }


    public synchronized void close() {
        isRunning = false;
        this.interrupt();
        try {
            this.socket.close();
        } catch (IOException ignored) {}
    }
}
