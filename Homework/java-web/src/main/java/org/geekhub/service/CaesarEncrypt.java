package org.geekhub.service;

import org.geekhub.annotation.Injectable;

public class CaesarEncrypt {

    @Injectable(property = "caesar.offset")
    private final int offset;
    private final StringBuilder encryptedMessage;


    public CaesarEncrypt() {
        AppConfig appConfig = new AppConfig();
        appConfig.loadProperties(CaesarEncrypt.class);
        this.offset = appConfig.getOffset();
        this.encryptedMessage = new StringBuilder();
    }

    public String cipher(String message) {
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
