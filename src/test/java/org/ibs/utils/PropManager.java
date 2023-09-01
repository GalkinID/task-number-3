package org.ibs.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropManager {
    public static Properties properties = new Properties();

    public PropManager() {
        loadProperties();
    }

    private void loadProperties() {
        try {
            properties.load(new FileInputStream(new File("src/test/resources/application.properties")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
