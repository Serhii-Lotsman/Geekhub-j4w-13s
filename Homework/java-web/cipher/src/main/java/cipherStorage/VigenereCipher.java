package cipherStorage;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VigenereCipher implements Cipher {

    private static final int LETTERS_OF_ALPHABET = 26;

    private final String key;

    public VigenereCipher(String key) {
        this.key = key;
    }

    @Override
    public String encrypt(String message) {
        return modifyMessage(message, true);
    }

    @Override
    public String decrypt(String encryptedMessage) {
        return modifyMessage(encryptedMessage, false);
    }

    private String modifyMessage(String text, boolean isEncrypt) {
        if (text == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        return IntStream.range(0, text.length())
            .mapToObj(index -> getCharacter(text, isEncrypt, index))
            .map(Object::toString)
            .collect(Collectors.joining());
    }

    private Character getCharacter(String text, boolean isEncrypt, int index) {
        char currentChar = text.charAt(index);
        if (Character.isLetter(currentChar)) {
            char base = Character.isUpperCase(currentChar) ? 'A' : 'a';
            int offset = key.toUpperCase().charAt(index % key.length()) - 'A';
            int modifiedChar = isEncrypt
                ? (currentChar - base + offset) % LETTERS_OF_ALPHABET
                : (currentChar - base - offset + LETTERS_OF_ALPHABET) % LETTERS_OF_ALPHABET;
            return (char) (modifiedChar + base);
        }
        return currentChar;
    }
}
