package org.geekhub.cipher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VigenereCipherTest {

    private VigenereCipher vigenereCipher;

    @BeforeEach
    void setUp() {
        vigenereCipher = new VigenereCipher("Key");
    }

    @Test
    void encrypt_shouldReturnEncryptedMessage_whenValidMessage() {
        String encryptedMessage = vigenereCipher.encrypt("Hello, World!");

        assertEquals("Rijvs, Ambpb!", encryptedMessage);
    }

    @Test
    void encrypt_shouldThrowException_whenNullMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> vigenereCipher.encrypt(null));

        assertEquals("Message cannot be null", exception.getMessage());
    }

    @Test
    void encrypt_shouldReturnEncryptedEmptyMessage_whenEmptyMessage() {
        String encryptedMessage = vigenereCipher.encrypt("");

        assertEquals("", encryptedMessage);
    }

    @Test
    void encrypt_shouldNotEncryptSpecialCharacters_whenMessageHasSpecialCharacters() {
        String encryptedMessage = vigenereCipher.encrypt("!@#$%^&*(),:.[]'`_-");

        assertEquals("!@#$%^&*(),:.[]'`_-", encryptedMessage);
    }

    @Test
    void encrypt_shouldReturnValidEncryptedMessage_whenKeyLongerThanMessage() {
        VigenereCipher longKeyCipher = new VigenereCipher("ABCDEFG");
        String encryptedMessage = longKeyCipher.encrypt("Hello");

        assertEquals("Hfnos", encryptedMessage);
    }

    @Test
    void decrypt_shouldReturnDecryptedMessage_whenValidEncryptedMessage() {
        String decryptedMessage = vigenereCipher.decrypt("Rijvs, Ambpb!");

        assertEquals("Hello, World!", decryptedMessage);
    }

    @Test
    void decrypt_shouldThrowException_whenNullMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> vigenereCipher.decrypt(null));

        assertEquals("Message cannot be null", exception.getMessage());
    }

    @Test
    void decrypt_shouldReturnDecryptedEmptyMessage_whenEmptyMessage() {
        String decryptedMessage = vigenereCipher.decrypt("");

        assertEquals("", decryptedMessage);
    }

    @Test
    void decrypt_shouldNotDecryptSpecialCharacters_whenMessageHasSpecialCharacters() {
        String decryptedMessage = vigenereCipher.decrypt("!@#$%^&*(),:.[]'`_-");

        assertEquals("!@#$%^&*(),:.[]'`_-", decryptedMessage);
    }

    @Test
    void decrypt_shouldReturnValidDecryptedMessage_whenKeyLongerThanMessage() {
        VigenereCipher longKeyCipher = new VigenereCipher("ABCDEFG");
        String decryptedMessage = longKeyCipher.decrypt("Hfnos");

        assertEquals("Hello", decryptedMessage);
    }
}

