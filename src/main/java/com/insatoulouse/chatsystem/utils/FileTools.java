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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * File tools class
 */
public class FileTools {

    /**
     * Get temp directory
     *
     * @param filename file to save
     * @return absolute path with file
     */
    public static String getTempFilename(String filename) {
        String tmppath = System.getProperty("java.io.tmpdir");
        return tmppath + "/" + filename.replace("/", "").replace("\\", "");
    }

    public static OutputStream getTempOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(filename);
    }

}
