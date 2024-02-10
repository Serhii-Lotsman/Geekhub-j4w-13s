package org.geekhub.service.cipher;

public interface Cipher {
    String encrypt(String message);

    String decrypt(String message);
}
