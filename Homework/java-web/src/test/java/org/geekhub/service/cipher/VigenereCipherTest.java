package org.geekhub.service.cipher;

import org.geekhub.exception.EncryptException;
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
    void encrypt_shouldThrowEncryptException_whenNullMessage() {
        EncryptException exception = assertThrows(EncryptException.class, () -> vigenereCipher.encrypt(null));

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
}

