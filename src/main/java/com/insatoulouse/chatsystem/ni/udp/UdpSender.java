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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * UdpSender class
 */
public class UdpSender {

    private static final Logger l = LogManager.getLogger(UdpSender.class.getName());

    /**
     * Send an UdpPacket
     * @param packet DatagramPacket to send
     * @throws TechnicalException
     */
    public void send(DatagramPacket packet) throws TechnicalException {
        l.debug("Send : " + packet);
        try {
            DatagramSocket s = new DatagramSocket();
            s.send(packet);
        } catch (SocketException e) {
            l.error("Fail to send Udp packet", e);
            throw new TechnicalException("Fail to send Udp packet", e);
        } catch (IOException e) {
            l.error("Fail to send Udp packet", e);
            throw new TechnicalException("Fail to send Udp packet", e);
        }
    }

}
