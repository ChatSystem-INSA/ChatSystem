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

package com.insatoulouse.chatsystem.ni.udp;

import com.insatoulouse.chatsystem.exception.TechnicalException;
import com.insatoulouse.chatsystem.ni.ChatNI;
import com.insatoulouse.chatsystem.utils.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * UdpListener class
 * Start a new Thread with Listen socket
 * Send received data to ChatNI
 *
 * @see UdpSocket
 */
public class UdpListener extends Thread {

    private static final Logger l = LogManager.getLogger(UdpListener.class.getName());

    private ChatNI chatNI;
    private UdpSocket socket;
    private Boolean isRunning = true;

    public UdpListener(ChatNI chatNI) throws TechnicalException {
        l.trace("Create UdpListener");
        this.chatNI = chatNI;
        try {
            this.socket = new UdpSocket(Integer.parseInt(Config.getInstance().getProperties(Config.CONFIG_PORT)));
        } catch (IOException e) {
            l.error("Fail to launch UdpListener",e);
            throw new TechnicalException("Impossible de lancer le UDPListener.",e);
        }
    }

    public void run()
    {
        l.trace("Start UdpListener");
        byte buffer[] = new byte[4096];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while(isRunning){
            try {
                this.socket.receive(packet);
                chatNI.processPacket(packet);
            } catch (IOException ignored) {
                l.warn(ignored);
            }
        }
    }

    public synchronized void close() {
        l.trace("Close UdpListener");
        isRunning = false;
        this.socket.close();
        this.interrupt();
    }

}
