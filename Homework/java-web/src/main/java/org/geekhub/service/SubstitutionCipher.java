package org.geekhub.service;

import org.geekhub.annotation.Injectable;
import org.geekhub.exception.EncryptException;

import java.util.HashMap;
import java.util.Map;

public class SubstitutionCipher {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final Map<Character, Character> substitutionKey;
    @Injectable(property = "substitution.key")
    private final String key;

    public SubstitutionCipher() {
        AppConfig appConfig = new AppConfig();
        appConfig.loadProperties(SubstitutionCipher.class);
        this.key = appConfig.getProperty().toString();
        if (key.length() != 26) {
            throw new EncryptException("Key must be a permutation of the alphabet.");
        }

        substitutionKey = new HashMap<>();
        for (int i = 0; i < ALPHABET.length(); i++) {
            substitutionKey.put(ALPHABET.charAt(i), key.charAt(i));
            substitutionKey.put(Character.toLowerCase(ALPHABET.charAt(i)), Character.toLowerCase(key.charAt(i)));
        }
    }

    public String encrypt(String message) {
        StringBuilder encryptedMessage = new StringBuilder();

        for (char character : message.toCharArray()) {
            if (Character.isLetter(character)) {
                char encryptedCharacter = substitutionKey.getOrDefault(character, character);
                encryptedMessage.append(encryptedCharacter);
            } else {
                encryptedMessage.append(character);
            }
        }

        return encryptedMessage.toString();
    }
}
