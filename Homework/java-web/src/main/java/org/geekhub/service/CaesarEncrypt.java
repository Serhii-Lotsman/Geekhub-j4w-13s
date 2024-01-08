package org.geekhub.service;

import org.geekhub.annotation.Injectable;

public class CaesarEncrypt {

    @Injectable()
    private int defaultOffset;
    private final StringBuilder encryptedMessage;


    public CaesarEncrypt() {
        this.encryptedMessage = new StringBuilder();
    }

    public String cipher(String message, int offset) {
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
