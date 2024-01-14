package org.geekhub.consoleapi;

import org.geekhub.exception.EncryptException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class EncryptSetter {
    private final OffsetDateTime dateTime;
    private final CipherManager manager;

    public EncryptSetter() {
        this.dateTime = OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        this.manager = new CipherManager();
    }
    public void setEncrypt(Scanner scanner, Class<?> instance) {
        Object classObject;
        Set<Method> methods = Set.of(instance.getDeclaredMethods());

        try {
            classObject = instance.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new EncryptException("Error creating an instance of " + instance.getName());
        }

        Optional<Method> getMethod = methods.stream()
            .filter(method -> method.getName().equals("encrypt")).findFirst();
        Method method = getMethod.orElseGet(getMethod::get);

        System.out.println("Enter a message to encrypt: ");
        String originalMessage = scanner.nextLine();
        String encryptedMessage = invokeMethod(method, classObject, originalMessage);
        manager.saveLogs(originalMessage, encryptedMessage, dateTime, classObject);
    }

    @SuppressWarnings("java:S3011")
    private String invokeMethod(Method method, Object object, String originalMessage) {
        String encryptedMessage;
        try {
            method.setAccessible(true);
            encryptedMessage = method.invoke(object, originalMessage).toString();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new EncryptException(e.getMessage());
        }
        return encryptedMessage;
    }
}
