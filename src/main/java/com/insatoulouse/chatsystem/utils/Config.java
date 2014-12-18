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

import com.insatoulouse.chatsystem.exception.TechnicalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Load config in project
 * Singleton
 */
public class Config {

    public static final String CONFIG_PORT = "port";
    private static final String FILE_CONFIG = "/config.properties";
    private static final Logger logger = LogManager.getLogger(Config.class.getName());
    private static Config instance = null;
    private Properties properties;

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
        if (instance == null)
            instance = new Config();
        return instance;
    }

    /**
     * Get properties corresponding with key
     *
     * @param name key
     * @return properties
     * @throws TechnicalException
     */
    public String getProperties(String name) throws TechnicalException {
        String propertie = properties.getProperty(name);
        if (propertie == null) {
            logger.error("Property not found : " + Config.CONFIG_PORT);
            throw new TechnicalException("Property not found : " + Config.CONFIG_PORT);
        }
        return propertie;
    }

}
