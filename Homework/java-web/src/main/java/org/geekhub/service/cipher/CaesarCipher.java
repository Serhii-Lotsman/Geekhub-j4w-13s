package org.geekhub.service.cipher;

import org.geekhub.exception.EncryptException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CaesarCipher implements Cipher {

    private final int offset;

    public CaesarCipher(@Value("${cipher.caesar}") int offset) {
        this.offset = Math.abs(offset);
    }

    public String encrypt(String message) {
        if (message == null) {
            throw new EncryptException("Message cannot be null");
        }
        if (offset%26 == 0) {
            throw new EncryptException("The key cannot be zero or multiple of 26");
        }

        return message.chars()
            .mapToObj(this::getCharacter)
            .map(Object::toString)
            .collect(Collectors.joining());
    }

    private Character getCharacter(int character) {
        if (Character.isLetter(character)) {
            char base = Character.isUpperCase(character) ? 'A' : 'a';
            return (char) ((character - base + offset) % 26 + base);
        }

        return (char) character;
    }
}
