package com.insatoulouse.chatsystem.utils;

import com.insatoulouse.chatsystem.exception.TechnicalException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Load config in project
 * Singleton
 */
public class Config {

    private static final String FILE_CONFIG = "/config.properties";
    private static Logger logger = LogManager.getLogger(Config.class.getName());
    private Properties properties;

    private static Config instance = null;

    private Config() throws TechnicalException {
        try {
            properties = new Properties();
            properties.load(getClass().getResourceAsStream(FILE_CONFIG));
        } catch (FileNotFoundException e) {
            logger.error("Error to load config file", e);
            throw new TechnicalException("Error to load config file");
        } catch (IOException e) {
            logger.error("Error to parse config file", e);
            throw new TechnicalException("Error to parse config file");
        }

    }

    public static synchronized Config getInstance() throws TechnicalException {
        if(instance == null)
            instance = new Config();
        return instance;
    }

    public String getProperties(String name) throws TechnicalException {
        String propertie = properties.getProperty(name);
        if(propertie == null){
            logger.error("Property not found : "+name);
            throw new TechnicalException("Property not found : "+name);
        }
        return propertie;
    }

}
