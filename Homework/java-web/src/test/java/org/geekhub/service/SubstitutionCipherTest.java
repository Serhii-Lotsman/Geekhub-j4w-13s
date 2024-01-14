package org.geekhub.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubstitutionCipherTest {

    private SubstitutionCipher substitutionCipher;

    @BeforeEach
    void setUp() {
        this.substitutionCipher = new SubstitutionCipher();
    }

    @Test
    void encrypt_whenValidMessage_shouldReturnEncryptedMessage() {
        String originalMessage = "Hello, World!";
        String encryptedMessage = substitutionCipher.encrypt(originalMessage);
        assertEquals("Oiwwd, Tdjwg!", encryptedMessage);
    }

    @Test
    void encrypt_whenEmptyMessage_shouldReturnEmptyMessage() {
        String originalMessage = "";
        String encryptedMessage = substitutionCipher.encrypt(originalMessage);
        assertEquals("", encryptedMessage);
    }

    @Test
    void encrypt_whenNullMessage_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> substitutionCipher.encrypt(null));
    }
}

