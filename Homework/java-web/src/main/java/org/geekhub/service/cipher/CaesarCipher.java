package org.geekhub.service.cipher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Profile("caesarCipher")
@Service
public class CaesarCipher implements Cipher {

    @Value("${cipher.caesar}")
    private int offset;

    public String encrypt(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
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
