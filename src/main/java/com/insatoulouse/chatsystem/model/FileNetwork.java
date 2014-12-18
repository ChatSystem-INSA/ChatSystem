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

package com.insatoulouse.chatsystem.model;

import java.io.File;

/**
 * FileNetwork class
 * Specific MessageNetwork when receiving or sending a file
 */
public class FileNetwork extends MessageNetwork {

    /**
     * File
     */
    private File file;

    public FileNetwork(int type, RemoteUser u, File file) {
        super(type, u, "");
        if (type == MessageNetwork.OUT) {
            setMessage("Envoi du fichier " + file.getName());
        } else {
            setMessage("Reception d'un fichier. Enregisté : " + file.getAbsolutePath());
        }

        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
