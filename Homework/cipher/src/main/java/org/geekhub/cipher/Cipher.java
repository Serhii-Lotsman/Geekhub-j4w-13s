package org.geekhub.cipher;

public interface Cipher {
    String encrypt(String message);

    String decrypt(String encryptedMessage);
}
