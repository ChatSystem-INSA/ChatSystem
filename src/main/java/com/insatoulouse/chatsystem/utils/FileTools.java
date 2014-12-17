package com.insatoulouse.chatsystem.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by tlk on 17/12/14.
 */
public class FileTools {

    public static String getTempFilename(String filename)
    {
        String tmppath = System.getProperty("java.io.tmpdir");
        return tmppath + "/" + filename.replace("/", "").replace("\\", "");
    }

    public static OutputStream getTempOutputStream(String filename) throws FileNotFoundException
    {
        return  new FileOutputStream(filename);
    }

}
