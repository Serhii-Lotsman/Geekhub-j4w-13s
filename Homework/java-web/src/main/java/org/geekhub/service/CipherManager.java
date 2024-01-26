package org.geekhub.service;

public class CipherManager {
    private final Cipher cipher;

    public CipherManager(Cipher cipher) {
        this.cipher = cipher;
    }

    public String getEncryptedMessage(String message) {
        return cipher.encrypt(message);
    }
}
