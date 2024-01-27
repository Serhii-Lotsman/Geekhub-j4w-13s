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
    void encrypt_whenEmptyMessage_shouldReturnEmptyMessage() {
        String originalMessage = "";
        String encryptedMessage = caesarCipher.encrypt(originalMessage);
        assertEquals("", encryptedMessage);
    }

    @Test
    void encrypt_whenNullMessage_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> caesarCipher.encrypt(null));
    }
}
