package org.geekhub.service.cipher;

import org.geekhub.exception.EncryptException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Profile("vigenereCipher")
@Service
public class VigenereCipher implements Cipher {

    private final String key;

    public VigenereCipher(@Value("${cipher.vigenere}") String key) {
        this.key = key;
    }

    public String encrypt(String message) {
        if (message == null) {
            throw new EncryptException("Message cannot be null");
        }

        return IntStream.range(0, message.length())
            .mapToObj(index -> getEncryptedChar(message, index))
            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
            .toString();
    }

    private char getEncryptedChar(String message, int index) {
        char currentChar = message.charAt(index);

        if (Character.isLetter(currentChar)) {
            char base = Character.isUpperCase(currentChar) ? 'A' : 'a';
            int offset = key.toUpperCase().charAt(index % key.length()) - 'A';
            return (char) ((currentChar - base + offset) % 26 + base);
        }
        return currentChar;
    }
}
