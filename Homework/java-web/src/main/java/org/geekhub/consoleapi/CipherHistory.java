package org.geekhub.consoleapi;

import org.geekhub.repository.LogInMemory;
import org.geekhub.repository.LogRepository;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CipherHistory {

    private static final String COUNT_OF_USAGE = "-------------------------------" +
        "C-O-U-N-T--O-F--U-S-A-G-E" +
        "-------------------------------";
    private static final String CURRENT_MESSAGE = "-----------------------------" +
        "C-U-R-R-E-N-T--M-E-S-S-A-G-E" +
        "------------------------------";
    private static final String END_LINE = "-------------------------------------" +
        "--------------------------------------------------";
    private static final String HISTORY = "-------------------------------------" +
        "H-I-S-T-O-R-Y" +
        "-------------------------------------";
    private static final String HISTORY_EMPTY = "History empty";
    private static final String MESSAGES_BY_DATE = "-----------------------------" +
        "M-E-S-S-A-G-E-S--B-Y--D-A-T-E" +
        "-----------------------------";
    private static final String UNIQUE_MESSAGE = "-----------------------------" +
        "U-N-I-Q-U-E---M-E-S-S-A-G-E-S" +
        "-----------------------------";
    private static final int INDEX_BEGIN_DATE = 0;
    private static final int INDEX_END_DATE = 10;
    private final LogRepository logRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


    public CipherHistory() {
        this.logRepository = new LogInMemory();
    }

    public void saveCurrentMessage(String originalMessage, String encryptedMessage, OffsetDateTime dateTime, String encryptor) {
        String message = dateTime.format(formatter) + String.format(
            " - Message '%s' was encrypted via %s into '%s'",
            originalMessage, encryptor, encryptedMessage);
        logRepository.addMessage(message);
        logRepository.saveHistory();

        printCurrentMessage(message);
    }

    private void printCurrentMessage(String message) {
        System.out.println(CURRENT_MESSAGE);
        System.out.println(message);
        System.out.println(END_LINE);
    }

    public void getCountOfUsage() {
        List<String> messages = logRepository.loadHistory();
        Map<String, Integer> statistic = messages.stream()
            .map(message -> message.substring(message.indexOf("via") + "via".length(),
                message.indexOf("into")).trim())
            .collect(HashMap::new, (map, algorithmName) -> map.merge(algorithmName, 1, Integer::sum), HashMap::putAll);

        printCountOfUsage(statistic);
    }


    private void printCountOfUsage(Map<String, Integer> statistic) {
        System.out.println(COUNT_OF_USAGE);
        if (statistic.isEmpty()) {
            System.out.println(HISTORY_EMPTY);
        } else {
            statistic.forEach((algorithmType, count) ->
                System.out.printf("%s was used %s times%n", algorithmType, count));
        }
        System.out.println(END_LINE);
    }

    public void getLoadedHistory() {
        List<String> allHistory = logRepository.loadHistory();
        if (allHistory.isEmpty()) {
            System.out.println(HISTORY_EMPTY);
        } else {
            printLoadedHistory(allHistory);
        }
    }

    private void printLoadedHistory(List<String> allHistory) {
        System.out.println(HISTORY);
        for (String message : allHistory) {
            System.out.println(message);
        }
        System.out.println(END_LINE);
    }

    public void getHistoryByDate(String specificDate) {
        List<String> messages = logRepository.loadHistory();
        Map<String, List<String>> historyByDate = messages.stream()
            .filter(message -> getMessageDate(message).equals(specificDate))
            .collect(Collectors.groupingBy(this::getMessageDate));

        if (historyByDate.isEmpty()) {
            System.out.println(HISTORY_EMPTY);
        } else {
            printHistoryByDate(historyByDate);
        }
    }

    private String getMessageDate(String message) {
        return message.substring(INDEX_BEGIN_DATE, INDEX_END_DATE);
    }

    private void printHistoryByDate(Map<String, List<String>> historyByDate) {
        System.out.println(MESSAGES_BY_DATE);
        historyByDate.forEach((date, dateMessages) -> {
            List<String> substrings = dateMessages.stream()
                .map(message -> message.substring(INDEX_END_DATE + 1)).toList();

            System.out.printf("[%s] %n%s %n", date, String.join("\n", substrings));
        });
        System.out.println(END_LINE);
    }

    public void getUniqueMessages() {
        boolean isDuplicateMessage = false;
        int uniqueCount = 1;
        List<String> messages = logRepository.loadHistory();

        Map<String, Long> uniqueMessages = getMapUniqueMessages(messages);
        printUniqueMessages(uniqueMessages, uniqueCount, isDuplicateMessage);
    }

    private Map<String, Long> getMapUniqueMessages(List<String> messages) {
        return messages.stream()
            .map(message -> message.substring(message.indexOf("Message"),
                message.indexOf("into")).trim())
            .collect(Collectors.groupingBy(
                message -> message,
                Collectors.counting()
            ));
    }

    private void printUniqueMessages(Map<String, Long> uniqueMessages, int uniqueCount, boolean isDuplicateMessage) {
        System.out.println(UNIQUE_MESSAGE);
        for (Map.Entry<String, Long> entry : uniqueMessages.entrySet()) {
            String message = entry.getKey();
            long count = entry.getValue();
            if (count > uniqueCount) {
                System.out.printf("%s %s times%n", message, count);
                isDuplicateMessage = true;
            }
        }
        if (!isDuplicateMessage) {
            System.out.println(HISTORY_EMPTY);
        }
        System.out.println(END_LINE);
    }
}
