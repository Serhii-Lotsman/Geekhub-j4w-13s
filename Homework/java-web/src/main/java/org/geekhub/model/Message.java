package org.geekhub.model;

import org.springframework.beans.factory.annotation.Value;

import java.time.format.DateTimeFormatter;

public class Message {
    private final long userId;
    private String originalMessage;
    private final String encryptedMessage;
    private final String algorithm;
    private final DateTimeFormatter date;

    public Message(@Value("${user.id}") long userId,
                   String originalMessage,
                   String encryptedMessage,
                   String algorithm,
                   DateTimeFormatter date) {
        this.userId = userId;
        this.originalMessage = originalMessage;
        this.encryptedMessage = encryptedMessage;
        this.algorithm = algorithm;
        this.date = date;
    }

    public long getUserId() {
        return userId;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }

    public String getEncryptedMessage() {
        return encryptedMessage;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public DateTimeFormatter getDate() {
        return date;
    }
}
