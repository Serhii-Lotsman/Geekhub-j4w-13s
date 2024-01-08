package org.geekhub.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EncryptionService {

    private int defaultOffset;

    public EncryptionService() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            defaultOffset = Integer.parseInt(properties.getProperty("encryption.defaultOffset", "3"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public int getDefaultOffset() {
        return defaultOffset;
    }
}

