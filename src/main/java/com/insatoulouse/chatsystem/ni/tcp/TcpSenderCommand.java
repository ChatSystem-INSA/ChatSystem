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
import com.insatoulouse.chatsystem.ni.NetworkCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

/**
 * TcpSenderCommand class
 */
public class TcpSenderCommand implements NetworkCommand {

    private static final Logger l = LogManager.getLogger(TcpSenderCommand.class.getName());

    private final TcpSender tcpSender;
    private final File f;
    private final InetAddress addr;

    public TcpSenderCommand(File f, InetAddress addr) {
        l.trace("Create TcpSenderCommand");
        this.f = f;
        this.addr = addr;
        this.tcpSender = new TcpSender();
    }

    @Override
    public void execute() {
        l.trace("Execute TcpSenderCommand");
        try {
            tcpSender.send(f, addr);
        } catch (TechnicalException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
