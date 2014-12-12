package com.insatoulouse.chatsystem.model;

import java.io.File;

public class FileNetwork extends MessageNetwork {

    private File file;

    public FileNetwork(RemoteUser u, File f) {
        super(MessageNetwork.OUT, u, "Reception du fichier "+f.getName());
        this.file = f;
    }

    public FileNetwork(int type, RemoteUser u, File file) {
        super(type, u, "");
        if(type == MessageNetwork.IN)
            setMessage("Envoi du fichier "+file.getName());
        else
            setMessage("Reception du fichier "+file.getName());

        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
