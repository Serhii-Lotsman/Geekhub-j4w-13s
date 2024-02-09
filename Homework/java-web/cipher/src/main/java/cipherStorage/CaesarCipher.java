package cipherStorage;

import java.util.stream.Collectors;

public class CaesarCipher implements Cipher {

    private static final int LETTERS_OF_ALPHABET = 26;
    private final int offset;

    public CaesarCipher(int offset) {
        this.offset = Math.abs(offset);
    }

    @Override
    public String encrypt(String message) {
        return modifyMessage(message, true);
    }

    @Override
    public String decrypt(String encryptedMessage) {
        return modifyMessage(encryptedMessage, false);
    }

    private String modifyMessage(String message, boolean isEncrypt) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        return message.chars()
            .mapToObj(index -> getCharacter(index, isEncrypt))
            .map(Object::toString)
            .collect(Collectors.joining());
    }

    private Character getCharacter(int currentCharacter, boolean isEncrypt) {
        if (Character.isLetter(currentCharacter)) {
            char base = Character.isUpperCase(currentCharacter) ? 'A' : 'a';
            int modifiedChar = isEncrypt
                ? (currentCharacter - base + offset) % LETTERS_OF_ALPHABET + base
                : (currentCharacter - base + LETTERS_OF_ALPHABET - offset) % LETTERS_OF_ALPHABET + base;
            if (modifiedChar < base) {
                modifiedChar += LETTERS_OF_ALPHABET;
            }
            return (char) modifiedChar;
        }
        return (char) currentCharacter;
    }
}
