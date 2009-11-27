package com.magic.utils;

import java.io.*;
import java.util.Properties;

public class PropertiesFileHandler {
    private static Properties props;

    static {
        props = new Properties();

        try {
            Class propertiesHandlerClass = Class.forName("com.magic.utils.PropertiesFileHandler");
            try {
                InputStream is = propertiesHandlerClass.getResourceAsStream("/portalApplication.properties");
                if(is != null) {
                    props.load(is);
                } else {
                }
            } catch(IOException ioe) {
            }

            try {
                InputStream is = propertiesHandlerClass.getResourceAsStream("/portalApplication.properties");
                if(is != null) {
                    props.load(is);
                } else {
                }
            } catch(IOException ioe) {
            }

        } catch (Exception e) {
        }
    }

    private PropertiesFileHandler() {
    }

    public static Properties getProperties() {
        return props;
    }

    public static String getProperty(String name) {
        return props.getProperty(name);
    }
}
