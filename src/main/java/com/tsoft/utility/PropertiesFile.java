package com.tsoft.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {

    public Properties getProperty() {
        String dir = System.getProperty("user.dir") + "/src/main/resources/properties/";
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(dir + "hook.properties"));
            properties.load(new FileInputStream(dir + "fixtures.properties"));
            properties.load(new FileInputStream(dir + "manager.properties"));
            properties.load(new FileInputStream(dir + "report.properties"));
            properties.load(new FileInputStream(dir + "klov.properties"));
            properties.load(new FileInputStream(dir + "database.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }
}
