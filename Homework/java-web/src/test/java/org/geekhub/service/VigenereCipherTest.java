package org.geekhub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VigenereCipherTest {

    private VigenereCipher vigenereCipher;

    @BeforeEach
    void setUp() {
        vigenereCipher = new VigenereCipher();
    }

    @Test
    void encrypt_whenValidMessage_shouldReturnEncryptedMessage() {
        String encryptedMessage = vigenereCipher.encrypt("Hello, World!");
        assertEquals("Rijvs, Uyvjn!", encryptedMessage);
    }

    @Test
    void encrypt_whenEmptyMessage_shouldReturnEmptyMessage() {
        String originalMessage = "";
        String encryptedMessage = vigenereCipher.encrypt(originalMessage);
        assertEquals("", encryptedMessage);
    }

    @Test
    void encrypt_whenNullMessage_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> vigenereCipher.encrypt(null));
    }
}
