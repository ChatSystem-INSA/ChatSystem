package com.insatoulouse.chatsystem.model;

import java.io.File;

public class FileNetwork extends MessageNetwork {

    private File file;


    public FileNetwork(int type, RemoteUser u, File file) {
        super(type, u, "");
        if(type == MessageNetwork.OUT)
            setMessage("Envoi du fichier "+file.getName());
        else
            setMessage("Reception d'un fichier. Enregisté : "+file.getAbsolutePath());

        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
