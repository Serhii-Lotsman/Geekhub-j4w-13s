package org.geekhub.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CaesarCipher implements Cipher {

    @Value("${cipher.caesar}")
    private int offset;

    public String encrypt(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        StringBuilder encryptedMessage = new StringBuilder();
        for (char character : message.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                encryptedMessage.append((char) ((character - base + offset) % 26 + base));
            } else {
                encryptedMessage.append(character);
            }
        }
        return encryptedMessage.toString();
    }
}
