package org.geekhub.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VigenereCipher implements Cipher {

    @Value("${cipher.vigenere}")
    private String key;

    public String encrypt(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0, j = 0; i < message.length(); i++) {
            char currentChar = message.charAt(i);
            char encryptedChar;

            if (Character.isLetter(currentChar)) {
                char base = Character.isUpperCase(currentChar) ? 'A' : 'a';
                int offset = key.toUpperCase().charAt(j) - 'A';
                encryptedChar = (char) ((currentChar - base + offset) % 26 + base);
                j = (j + 1) % key.length();
            } else {
                encryptedChar = currentChar;
            }

            encryptedMessage.append(encryptedChar);
        }

        return encryptedMessage.toString();
    }
}
