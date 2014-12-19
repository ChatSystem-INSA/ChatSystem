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

package com.insatoulouse.chatsystem.utils;

import com.insatoulouse.chatsystem.exception.TechnicalException;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * NetworkTools class
 */
public class NetworkTools {

    /**
     * Get string of datagramPacket
     *
     * @param packet packet
     * @return string
     */
    public static String getString(DatagramPacket packet) {
        return new String(packet.getData(), packet.getOffset(), packet.getLength());
    }

    /**
     * Get datagram packet from data string and an inetAdress
     *
     * @param data data receiving
     * @param addr address ip corresponding
     * @return datagram packet
     * @throws TechnicalException
     */
    public static DatagramPacket getDatagramPacket(String data, InetAddress addr) throws TechnicalException {
        byte[] bytes = data.getBytes(Charset.defaultCharset());
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
        dp.setAddress(addr);
        dp.setPort(NetworkTools.getPort());
        return dp;
    }

    /**
     * Get list of broadcast address
     *
     * @return list
     * @throws SocketException
     */
    public static ArrayList<InetAddress> getBroadcastAddr() throws SocketException {
        Enumeration list = NetworkInterface.getNetworkInterfaces();
        ArrayList<InetAddress> ret = new ArrayList<InetAddress>();

        while (list.hasMoreElements()) {
            NetworkInterface iface = (NetworkInterface) list.nextElement();

            if (iface != null && !iface.isLoopback() && iface.isUp()) {
                for (InterfaceAddress address : iface.getInterfaceAddresses()) {
                    if (address != null) {
                        InetAddress broadcast = address.getBroadcast();
                        if (broadcast != null) {
                            ret.add(broadcast);
                        }
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Get DataInputStream from socket
     *
     * @param s soket
     * @return DataInputStream
     * @throws IOException
     */
    public static DataInputStream getDataInputStreamFromSocket(Socket s) throws IOException {
        InputStream in = s.getInputStream();
        return new DataInputStream(in);
    }

    public static Integer getPort() throws TechnicalException {
        return Integer.parseInt(Config.getInstance().getProperties(Config.CONFIG_PORT));
    }

}
