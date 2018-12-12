package org.mlesyk.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Maks.
 */
public class PropertyLoader {

    private static final String PROP_FILE_PATH = "/application.properties";
    private static Properties properties;

    private PropertyLoader() throws IOException {
    }

    public static String getProperty(String key) {
        if (properties == null) {
            loadProperties();
        }
        return System.getProperty(key) == null ? properties.getProperty(key) : System.getProperty(key);
    }

    private static void loadProperties() {
        properties = new Properties();
        try {
            properties.load(PropertyLoader.class.getResourceAsStream(PROP_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        printAllProperties();
    }

    public static void printAllProperties() {
        loadProperties();
        properties.entrySet().forEach(p -> System.out.println(p.getKey() + "=" + p.getValue()));
    }
}
