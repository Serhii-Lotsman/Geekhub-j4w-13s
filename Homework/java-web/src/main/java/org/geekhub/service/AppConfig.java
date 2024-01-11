package org.geekhub.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private final Properties properties;
    private int offset;

    public AppConfig() {
        this.properties = new Properties();
    }

    public void loadProperties() {
        try (InputStream input = getClass()
            .getClassLoader()
            .getResourceAsStream("application.properties")) {

            properties.load(input);
            String getProperty = properties.getProperty("encryption.offset");
            offset = getProperty == null ? 0 : Integer.parseInt(getProperty);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public int getOffset() {
        return offset;
    }
}

