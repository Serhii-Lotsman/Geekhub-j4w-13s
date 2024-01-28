package org.geekhub.service;

import org.geekhub.consoleapi.HistoryPrinter;
import org.geekhub.repository.LogRepository;
import org.geekhub.service.cipher.Cipher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HistoryManager {
    private static final int INDEX_BEGIN_DATE = 0;
    private static final int INDEX_END_DATE = 10;

    private final LogRepository logRepository;
    private final HistoryPrinter historyPrinter;
    private final OffsetDateTime dateTime;
    private final DateTimeFormatter formatter;
    private final Cipher cipher;
    private final String originalMessage;
    private final String specificDate;

    public HistoryManager(@Value("${message.to.encrypt}") String originalMessage,
                          @Value("${message.by.date}") String specificDate,
                          HistoryPrinter historyPrinter,
                          LogRepository logRepository,
                          Cipher cipher) {
        this.originalMessage = originalMessage;
        this.specificDate = specificDate;
        this.logRepository = logRepository;
        this.historyPrinter = historyPrinter;
        this.cipher = cipher;
        this.dateTime = OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        this.formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }

    public void print(String encryptor) {
        getAllHistory();
        getCountOfUsage();
        getUniqueMessages();
        getMessageByDate(specificDate);

        saveMessage(originalMessage, cipher.encrypt(originalMessage), encryptor);
    }

    private void saveMessage(String originalMessage, String encryptedMessage, String encryptor) {
        String message = dateTime.format(formatter) + String.format(
            " - Message '%s' was encrypted via %s into '%s'",
            originalMessage, encryptor, encryptedMessage);
        logRepository.addMessage(message);
        logRepository.saveHistory();

        historyPrinter.printCurrentMessage(message);
    }

    private void getAllHistory() {
        List<String> allHistory = logRepository.loadHistory();
        historyPrinter.printLoadedHistory(allHistory);
    }

    private void getCountOfUsage() {
        List<String> messages = logRepository.loadHistory();
        Map<String, Integer> statistic = messages.stream()
            .map(message -> message.substring(
                message.indexOf("via") + "via".length(), message.indexOf("into")
            ).trim())
            .collect(HashMap::new, (map, algorithmName) ->
                map.merge(algorithmName, 1, Integer::sum), HashMap::putAll);

        historyPrinter.printCountOfUsage(statistic);
    }

    private void getUniqueMessages() {
        boolean isDuplicateMessage = false;
        int uniqueCount = 1;
        List<String> messages = logRepository.loadHistory();
        Map<String, Long> uniqueMessages = getMapUniqueMessages(messages);

        historyPrinter.printUniqueMessages(uniqueMessages, uniqueCount, isDuplicateMessage);
    }

    private Map<String, Long> getMapUniqueMessages(List<String> messages) {
        return messages.stream()
            .map(message -> message.substring(
                message.indexOf("Message"), message.indexOf("into")
            ).trim())
            .collect(Collectors.groupingBy(message ->
                message, Collectors.counting()));
    }

    private void getMessageByDate(String specificDate) {
        List<String> messages = logRepository.loadHistory();
        Map<String, List<String>> historyByDate = messages.stream()
            .filter(message -> getMessageDate(message).equals(specificDate))
            .collect(Collectors.groupingBy(this::getMessageDate));

        historyPrinter.printHistoryByDate(historyByDate);
    }

    private String getMessageDate(String message) {
        return message.substring(INDEX_BEGIN_DATE, INDEX_END_DATE);
    }
}
