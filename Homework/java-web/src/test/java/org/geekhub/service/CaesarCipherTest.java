package org.geekhub.service;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CaesarCipherTest {

    private CaesarCipher caesarCipher;

    @BeforeEach
    void setUp() {
        caesarCipher = new CaesarCipher();
    }

    @Test
    void encrypt_ValidMessage_ReturnsEncryptedMessage() {
        String originalMessage = "Hello, World!";
        String encryptedMessage = caesarCipher.encrypt(originalMessage);
        assertEquals("Khoor, Zruog!", encryptedMessage);
    }

    @Test
    void encrypt_EmptyMessage_ReturnsEmptyMessage() {
        String originalMessage = "";
        String encryptedMessage = caesarCipher.encrypt(originalMessage);
        assertEquals("", encryptedMessage);
    }

    @Test
    void encrypt_NullMessage_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> caesarCipher.encrypt(null));
    }
}
