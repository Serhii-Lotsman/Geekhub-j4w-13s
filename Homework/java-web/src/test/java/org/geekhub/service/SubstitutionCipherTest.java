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
    void encrypt_ValidMessage_ReturnsEncryptedMessage() {
        String originalMessage = "Hello, World!";
        String encryptedMessage = substitutionCipher.encrypt(originalMessage);
        assertEquals("Oiwwd, Tdjwg!", encryptedMessage);
    }

    @Test
    void encrypt_EmptyMessage_ReturnsEmptyMessage() {
        String originalMessage = "";
        String encryptedMessage = substitutionCipher.encrypt(originalMessage);
        assertEquals("", encryptedMessage);
    }

    @Test
    void encrypt_NullMessage_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> substitutionCipher.encrypt(null));
    }
}

