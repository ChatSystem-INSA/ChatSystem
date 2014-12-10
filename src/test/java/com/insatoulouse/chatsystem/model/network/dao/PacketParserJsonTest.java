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

package com.insatoulouse.chatsystem.model.network.dao;

import com.insatoulouse.chatsystem.exception.PacketException;
import com.insatoulouse.chatsystem.model.network.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class of json packet parser
 */
public class PacketParserJsonTest {
    private PacketParser parser = AbstractFactory.getFactory(AbstractFactory.Type.JSON).getPacketParser();

    /*
        Test packet
     */
    @Test(expected = PacketException.class)
    public void testReadBadPacket() throws PacketException{
        parser.read("{ \"type\":\"hello\" ");
    }

    @Test(expected = PacketException.class)
    public void testReadBadPacket2() throws PacketException{
        parser.read("[{ \"type\":\"hello\" }]");
    }

    @Test(expected = PacketException.class)
    public void testReadBadPacketWithoutType() throws PacketException{
        parser.read("{ }");
    }

    @Test(expected = PacketException.class)
    public void testReadBadPacketBadType() throws PacketException{
        parser.read("{ \"type\":null }");
    }

    /*
        Test hello
     */
    @Test
    public void testReadCorrectHello(){
        try {
            Packet p = parser.read("{ \"type\":\"hello\", \"userName\":\"toto\"}");
            assertTrue(p instanceof Hello);
        } catch (PacketException e) {
            fail();
        }
    }

    @Test(expected = PacketException.class)
    public void testReadBadHelloWithoutUserName() throws PacketException{
        parser.read("{ \"type\":\"hello\"}");
    }


    @Test(expected = PacketException.class)
          public void testReadBadHelloEmptyUserName() throws PacketException{
        parser.read("{ \"type\":\"hello\", \"userName\":\"\"}");
    }

    /*
        Test hello ack
     */

    @Test
    public void testReadCorrectHelloAck(){
        try {
            Packet p = parser.read("{ \"type\":\"helloAck\", \"userName\":\"toto\"}");
            assertTrue(p instanceof HelloAck);
        } catch (PacketException e) {
            fail();
        }
    }

    @Test(expected = PacketException.class)
    public void testReadBadHelloAckWithoutUserName() throws PacketException{
        parser.read("{ \"type\":\"helloAck\"}");
    }


    @Test(expected = PacketException.class)
    public void testReadBadHelloAckEmptyUserName() throws PacketException{
        parser.read("{ \"type\":\"helloAck\", \"userName\":\"\"}");
    }

    /*
        Test goodbye
     */

    @Test
    public void testReadCorrectGoodbye(){
        try {
            Packet p = parser.read("{ \"type\":\"goodBye\"}");
            assertTrue(p instanceof Goodbye);
        } catch (PacketException e) {
            fail();
        }
    }

    /*
        Test message
     */

    @Test
    public void testReadCorrectMessage(){
        try {
            Packet p = parser.read("{ \"type\":\"message\", \"messageData\":\"toto\", \"messageNumber\":1}");
            assertTrue(p instanceof Message);
        } catch (PacketException e) {
            fail();
        }
    }

    @Test(expected = PacketException.class)
    public void testReadBadMessageWithoutMessageData() throws PacketException{
        parser.read("{ \"type\":\"message\", \"messageNumber\":1}");
    }

    @Test(expected = PacketException.class)
    public void testReadBadMessageWithoutMessageNumber() throws PacketException{
        parser.read("{ \"type\":\"message\", \"messageData\":\"toto\"}");
    }

    @Test(expected = PacketException.class)
    public void testReadBadMessageWithoutNothing() throws PacketException{
        parser.read("{ \"type\":\"message\" }");
    }

    @Test(expected = PacketException.class)
    public void testReadBadNumberMessage() throws PacketException{
         parser.read("{ \"type\":\"message\", \"messageData\":\"toto\", \"messageNumber\":\"tata\"}");
    }

}