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

/**
 * AbstractFactory class
 */
public abstract class AbstractFactory {

    /**
     * Return concrete factory
     *
     * @param choice of parser support
     * @return Concrete factory
     * @see com.insatoulouse.chatsystem.model.network.dao.AbstractFactory.Type
     */
    public static AbstractFactory getFactory(Type choice) {
        switch (choice) {
            case JSON:
                return JsonFactory.getInstance();
            default:
                return null;
        }
    }

    public abstract PacketParser getPacketParser();

    /**
     * Type available for Packet parser
     */
    public enum Type {
        JSON
    }
}