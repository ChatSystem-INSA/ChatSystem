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

package com.insatoulouse.chatsystem.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insatoulouse.chatsystem.model.network.Hello;
import com.insatoulouse.chatsystem.model.network.Packet;

import java.io.IOException;

/**
 * Created by david on 11/12/14.
 */
public class ParserJsonTest {

    public static void main(String[] str)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Hello h = new Hello("toto");
            String json = mapper.writeValueAsString(h);
            System.out.println(json);
            Packet p = mapper.readValue("{\"type\":\"helloAck\",\"userName\":\"toto\"}",Packet.class);
            if(p instanceof Hello){
                System.out.print("Yes");
            }
            System.out.println(p.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
