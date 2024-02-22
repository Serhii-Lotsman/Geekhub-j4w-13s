package org.geekhub.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;


public class Message {
    private Integer id;
    private long userId;
    private String originalMessage;
    private String encryptedMessage;
    private String algorithm;
    private String operation;
    private String date;

    public Message(@Nullable Integer id,
                   long userId,
                   @NonNull String originalMessage,
                   @NonNull String encryptedMessage,
                   @NonNull String algorithm,
                   @NonNull String operation,
                   @NonNull String date) {
        this.id = id;
        this.userId = userId;
        this.originalMessage = originalMessage;
        this.encryptedMessage = encryptedMessage;
        this.algorithm = algorithm;
        this.operation = operation;
        this.date = date;
    }

    public Message(){
    }

    public Integer getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public String getEncryptedMessage() {
        return encryptedMessage;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getOperation() {
        return operation;
    }

    public String getDate() {
        return date;
    }
}
