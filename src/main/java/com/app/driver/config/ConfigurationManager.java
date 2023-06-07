package com.app.driver.config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigurationManager {
    /**
     * Method to fetch values from properties files using org.aeonbits.owner.ConfigFactory
     * @return - Configuration
     */
    public static Configuration getConfig(){
        return ConfigFactory.create(Configuration .class);
    }
}
