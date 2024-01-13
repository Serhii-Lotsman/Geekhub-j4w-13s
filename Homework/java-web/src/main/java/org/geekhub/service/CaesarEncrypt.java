package org.geekhub.service;

import org.geekhub.annotation.Injectable;

public class CaesarEncrypt {

    @Injectable(property = "caesar.offset")
    private final int offset;

    public CaesarEncrypt() {
        AppConfig appConfig = new AppConfig();
        appConfig.loadProperties(CaesarEncrypt.class);
        this.offset = Integer.parseInt(appConfig.getProperty().toString());
    }

    public String cipher(String message) {
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
