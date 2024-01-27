package org.geekhub.consoleapi;

import org.geekhub.service.Cipher;
import org.geekhub.service.CipherManager;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class HistoryPrinter {
    private final CipherHistory cipherHistory = new CipherHistory();
    private final OffsetDateTime dateTime = OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());

    public void print(Cipher cipher, String encryptor) {
        CipherManager cipherManager = new CipherManager(cipher);

        String originalMessage = "Message";
        String specificDate = "27-01-2024"; //"dd-MM-yyyy"
        String encryptedMessage = cipherManager.getEncryptedMessage(originalMessage);

        printAllHistory();
        printCountOfUsage();
        printUniqueMessages();
        printMessageByDate(specificDate);
        printAndSaveMessage(originalMessage, encryptedMessage, encryptor);
    }

    private void printAndSaveMessage(String originalMessage, String encryptedMessage, String encryptor) {
        cipherHistory.saveLogs(originalMessage, encryptedMessage, dateTime, encryptor);
    }

    private void printAllHistory() {
        cipherHistory.getLoadHistory();
    }

    private void printCountOfUsage() {
        cipherHistory.getCountOfUsage();
    }

    private void printUniqueMessages() {
        cipherHistory.getUniqueMessages();
    }

    private void printMessageByDate(String specificDate) {
        cipherHistory.getMessagesByDate(specificDate);
    }
}
