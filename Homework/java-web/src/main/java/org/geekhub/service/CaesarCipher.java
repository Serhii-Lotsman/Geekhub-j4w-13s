package org.geekhub.service;

import org.geekhub.annotation.Injectable;

public class CaesarCipher {

    @Injectable(property = "caesar.offset")
    private final int offset;

    public CaesarCipher() {
        AppConfig appConfig = new AppConfig();
        appConfig.loadProperties(CaesarCipher.class);
        this.offset = Integer.parseInt(appConfig.getProperty().toString());
    }

    public String encrypt(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        StringBuilder encryptedMessage = new StringBuilder();
        for (char character : message.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                encryptedMessage.append((char) ((character - base + offset) % 26 + base));
            } else {
                encryptedMessage.append(character);
            }
        }
        return encryptedMessage.toString();
    }
}
