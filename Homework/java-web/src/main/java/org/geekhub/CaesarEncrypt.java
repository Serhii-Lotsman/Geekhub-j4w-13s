package org.geekhub;

public class CaesarEncrypt {

    private final StringBuilder stringBuilder;

    public CaesarEncrypt() {
        this.stringBuilder = new StringBuilder();
    }

    public String cipher(String message, int offset) {
        for (char character : message.toCharArray()) {
            if (character != ' ') {
                int originalAlphabetPosition = character - 'a';
                int newAlphabetPosition = (originalAlphabetPosition + offset) % 26;
                char newCharacter = (char) ('a' + newAlphabetPosition);
                stringBuilder.append(newCharacter);
            } else {
                stringBuilder.append(character);
            }
        }
        return stringBuilder.toString();
    }
}
