package org.geekhub.model;

public class Message {
    private final long userId;
    private final String originalMessage;
    private final String encryptedMessage;
    private final String algorithm;
    private final String date;
    private final String status;

    public Message(long userId,
                   String originalMessage,
                   String encryptedMessage,
                   String algorithm,
                   String date,
                   String status) {
        this.userId = userId;
        this.originalMessage = originalMessage;
        this.encryptedMessage = encryptedMessage;
        this.algorithm = algorithm;
        this.date = date;
        this.status = status;
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
}
