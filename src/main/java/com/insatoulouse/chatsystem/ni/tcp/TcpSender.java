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
import com.insatoulouse.chatsystem.utils.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TcpSender {

    private static final Logger l = LogManager.getLogger(TcpSender.class.getName());

    public void send(File f, InetAddress addr) throws TechnicalException, IOException {
        l.trace("send");
        Socket s = new Socket(addr, Integer.parseInt(Config.getInstance().getProperties(Config.CONFIG_PORT)));
        byte[] fileBytes = new byte[(int) f.length()];
        FileInputStream fileInputStream = new FileInputStream(f);
        // getting informations about the file to send
        BufferedInputStream bis = new BufferedInputStream(fileInputStream);
        DataInputStream dis = new DataInputStream(bis);
        dis.readFully(fileBytes, 0, fileBytes.length);

        // ready to send through the socket
        OutputStream socketOutput = s.getOutputStream();

        // sending file name and file size
        DataOutputStream dos = new DataOutputStream(socketOutput);
        dos.writeUTF(f.getName());
        dos.writeLong(fileBytes.length);
        dos.flush();

        // sending file data
        socketOutput.write(fileBytes, 0, fileBytes.length);
        socketOutput.flush();
    }
}
