package org.geekhub.service.cipher;

import org.geekhub.exception.EncryptException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CaesarCipherTest {

    private CaesarCipher caesarCipher;

    @BeforeEach
    void setUp() {
        caesarCipher = new CaesarCipher(3);
    }

    @Test
    void encrypt_shouldReturnEncryptedMessage_whenValidMessage() {
        String encryptedMessage = caesarCipher.encrypt("Hello, World!");

        assertEquals("Khoor, Zruog!", encryptedMessage);
    }

    @Test
    void encrypt_shouldThrowEncryptException_whenNullMessage() {
        EncryptException exception = assertThrows(EncryptException.class, () -> caesarCipher.encrypt(null));

        assertEquals("Message cannot be null", exception.getMessage());
    }

    @Test
    void encrypt_shouldReturnEncryptedEmptyMessage_whenEmptyMessage() {
        String encryptedMessage = caesarCipher.encrypt("");

        assertEquals("", encryptedMessage);
    }

    @Test
    void encrypt_shouldNotEncryptSpecialCharacters_whenMessageHasSpecialCharacters() {
        String encryptedMessage = caesarCipher.encrypt("!@#$%^&*(),:.[]'`_-");

        assertEquals("!@#$%^&*(),:.[]'`_-", encryptedMessage);
    }

    @Test
    void encrypt_shouldReturnValidEncryptedMessage_whenNegativeOffset() {
        CaesarCipher negativeOffsetCipher = new CaesarCipher(-2);
        String encryptedMessage = negativeOffsetCipher.encrypt("Hello, World!");

        assertEquals("Jgnnq, Yqtnf!", encryptedMessage);
    }

    @Test
    void encrypt_shouldReturnValidMessage_whenBigOffset() {
        CaesarCipher largeOffsetCipher = new CaesarCipher(155);
        String encryptedMessage = largeOffsetCipher.encrypt("Hello, World!");

        assertEquals("Gdkkn, Vnqkc!", encryptedMessage);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 26, -26, 52, -52, 78, 104})
    void encrypt_shouldThrowEncryptException_whenZeroOrMultiply26Offset(int offset) {
        CaesarCipher nullOffsetCipher = new CaesarCipher(offset);
        EncryptException exception = assertThrows(EncryptException.class, () -> nullOffsetCipher.encrypt(""));

        assertEquals("The key cannot be zero or multiple of 26", exception.getMessage());
    }
}

