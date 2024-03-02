package org.geekhub.cipher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void encrypt_shouldThrowException_whenNullMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> caesarCipher.encrypt(null));

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
        CaesarCipher negativeOffsetCipher = new CaesarCipher(-5);
        String encryptedMessage = negativeOffsetCipher.encrypt("Hello, World!");

        assertEquals("Mjqqt, Btwqi!", encryptedMessage);
    }

    @Test
    void encrypt_shouldReturnValidMessage_whenBigOffset() {
        CaesarCipher largeOffsetCipher = new CaesarCipher(155);
        String encryptedMessage = largeOffsetCipher.encrypt("Hello, World!");

        assertEquals("Gdkkn, Vnqkc!", encryptedMessage);
    }

    @Test
    void decrypt_shouldReturnDecryptedMessage_whenValidEncryptedMessage() {
        String decryptedMessage = caesarCipher.decrypt("Khoor, Zruog!");

        assertEquals("Hello, World!", decryptedMessage);
    }

    @Test
    void decrypt_shouldThrowException_whenNullMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> caesarCipher.decrypt(null));

        assertEquals("Message cannot be null", exception.getMessage());
    }

    @Test
    void decrypt_shouldReturnDecryptedEmptyMessage_whenEmptyMessage() {
        String decryptedMessage = caesarCipher.decrypt("");

        assertEquals("", decryptedMessage);
    }

    @Test
    void decrypt_shouldNotDecryptSpecialCharacters_whenMessageHasSpecialCharacters() {
        String decryptedMessage = caesarCipher.decrypt("!@#$%^&*(),:.[]'`_-");

        assertEquals("!@#$%^&*(),:.[]'`_-", decryptedMessage);
    }

    @Test
    void decrypt_shouldReturnValidDecryptedMessage_whenNegativeOffset() {
        CaesarCipher negativeOffsetCipher = new CaesarCipher(-5);
        String decryptedMessage = negativeOffsetCipher.decrypt("Mjqqt, Btwqi!");

        assertEquals("Hello, World!", decryptedMessage);
    }

    @Test
    void decrypt_shouldReturnValidMessage_whenBigOffset() {
        CaesarCipher largeOffsetCipher = new CaesarCipher(155);
        String decryptedMessage = largeOffsetCipher.decrypt("Gdkkn, Vnqkc!");

        assertEquals("Hello, World!", decryptedMessage);
    }
}

