package org.geekhub.service;

import org.geekhub.exception.EncryptException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private final Properties properties;
    private final InjectableExecutor executor;
    private Object property;

    public AppConfig() {
        this.properties = new Properties();
        this.executor = new InjectableExecutor();
    }

    public void loadProperties(Class<?> encryptClass) {
        try (InputStream input = getClass()
            .getClassLoader()
            .getResourceAsStream("application.properties")) {

            properties.load(input);
            String getProperty = properties.getProperty(
                executor.getAnnotatedFieldValue(encryptClass)
            );
            if (getProperty == null) {
                System.exit(1);
            } else
                property = getProperty;
        } catch (IOException e) {
            throw new EncryptException(e.getMessage());
        }
    }

    public Object getProperty() {
        return property;
    }
}

