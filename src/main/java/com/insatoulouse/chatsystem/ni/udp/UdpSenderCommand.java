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
import com.insatoulouse.chatsystem.ni.NetworkCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.DatagramPacket;

/**
 * UdpSenderCommand class
 * Command to send an Udp packet
 *
 * @see UdpSender
 */
public class UdpSenderCommand implements NetworkCommand {

    private static final Logger l = LogManager.getLogger(UdpSenderCommand.class.getName());

    private final UdpSender udpSender;
    private final DatagramPacket p;

    public UdpSenderCommand(DatagramPacket p) {
        l.trace("Create UdpSenderCommand");
        this.p = p;
        this.udpSender = new UdpSender();
    }

    @Override
    public void execute() throws TechnicalException {
        l.trace("Execute command");
        udpSender.send(p);
    }
}
