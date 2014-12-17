package com.insatoulouse.chatsystem.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by tlk on 17/12/14.
 */
public class FileTools {

    public static OutputStream getTempOutputstream(String filename) throws FileNotFoundException
    {
        String tmppath = System.getProperty("java.io.tmpdir");
        filename = tmppath + "/" + filename.replace("/", "").replace("\\", "");
        return  new FileOutputStream(filename);
    }

}
