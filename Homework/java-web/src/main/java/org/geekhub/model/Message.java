package org.geekhub.model;

public class Message {
    private long userId;
    private String originalMessage;
    private String encryptedMessage;
    private String algorithm;
    private String date;
    private String status;
    private String operation;

    public Message(long userId,
                   String originalMessage,
                   String encryptedMessage,
                   String algorithm,
                   String operation,
                   String date,
                   String status) {
        this.userId = userId;
        this.originalMessage = originalMessage;
        this.encryptedMessage = encryptedMessage;
        this.algorithm = algorithm;
        this.operation = operation;
        this.date = date;
        this.status = status;
    }

    public Message(){
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

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getOperation() {
        return operation;
    }
}
