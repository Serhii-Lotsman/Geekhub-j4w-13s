package org.geekhub.service;

import org.geekhub.exception.EncryptException;
import org.geekhub.repository.LogInMemory;
import org.geekhub.repository.LogRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class CipherManager {

    private final OffsetDateTime dateTime;
    private final LogRepository logHistory;

    public CipherManager() {
        this.dateTime = OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        this.logHistory = new LogInMemory();
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
        saveLogs(originalMessage, encryptedMessage, dateTime, classObject);
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

    private void saveLogs(String originalMessage, String encryptedMessage, OffsetDateTime dateTime, Object object) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm:ss");
        String message = dateTime.format(formatter) + String.format(
            " - Message '%s' was encrypted via %s into '%s'",
            originalMessage, object.getClass().getSimpleName(), encryptedMessage);
        logHistory.addMessage(message);
        System.out.println(logHistory.saveHistory());
    }

    public void getLoadHistory() {
        logHistory.loadHistory();
    }
}