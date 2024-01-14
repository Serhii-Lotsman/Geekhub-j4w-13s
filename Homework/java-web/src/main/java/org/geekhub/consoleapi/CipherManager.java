package org.geekhub.consoleapi;

import org.geekhub.repository.LogInMemory;
import org.geekhub.repository.LogRepository;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CipherManager {

    private final LogRepository logHistory;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


    public CipherManager() {
        this.logHistory = new LogInMemory();
    }

    public void saveLogs(String originalMessage, String encryptedMessage, OffsetDateTime dateTime, Object object) {
        String message = dateTime.format(formatter) + String.format(
            " - Message '%s' was encrypted via %s into '%s'",
            originalMessage, object.getClass().getSimpleName(), encryptedMessage);
        logHistory.addMessage(message);
        System.out.println(logHistory.saveHistory());
    }

    public void getCountOfUsage() {
        List<String> messages = logHistory.loadHistory();

        Map<String, Integer> statistic = messages.stream()
            .map(message -> message.substring(message.indexOf("via") + "via".length(),
                message.indexOf("into")).trim())
            .collect(HashMap::new, (map, algorithmName) -> map.merge(algorithmName, 1, Integer::sum), HashMap::putAll);

        statistic.forEach((algorithmType, count) ->
            System.out.printf("%s was used %s times%n", algorithmType, count));
    }

    public void getLoadHistory() {
        for (String message : logHistory.loadHistory()) {
            System.out.println(message);
        }
    }

    public void getMessagesByDate(Scanner scanner) {
        List<String> messages = logHistory.loadHistory();

        Map<String, List<String>> historyByDate = messages.stream()
            .collect(
                Collectors.groupingBy(
                    this::getMessageDate,
                    Collectors.mapping(message -> message.substring(11), Collectors.toList()))
            );
        getAvailableDates(historyByDate, scanner);
    }

    private String getMessageDate(String message) {
        return message.substring(0, 10);
    }

    private void getAvailableDates(Map<String, List<String>> historyByDate, Scanner scanner) {
        List<String> availableDates = historyByDate.keySet().stream().toList();
        int subOption;
        System.out.println("Available dates:");
        int index = 0;
        for (String element : availableDates) {
            System.out.printf("[%s] - %s%n", index++, element);
        }
        subOption = scanner.nextInt();
        if (subOption == availableDates.indexOf(availableDates.get(subOption))) {
            historyByDate.forEach((date, dateMessages) -> {
                if (availableDates.get(subOption).equals(date)) {
                    List<String> substrings = dateMessages.stream()
                        .map(message -> message.substring(11))
                        .toList();

                    System.out.printf("[%s] %n%s %n", date, String.join("\n", substrings));
                }
            });
        }
    }

    public void getUniqueMessages() {
        boolean foundDuplicates = false;
        List<String> messages = logHistory.loadHistory();

        Map<String, Long> uniqueMessages = messages.stream()
            .map(message -> message.substring(message.indexOf("Message"),
                message.indexOf("into")).trim())
            .collect(Collectors.groupingBy(
                message -> message,
                Collectors.counting()
            ));

        for (Map.Entry<String, Long> entry : uniqueMessages.entrySet()) {
            String message = entry.getKey();
            long count = entry.getValue();
            if (count > 1) {
                System.out.printf("%s %s times%n", message, count);
                foundDuplicates = true;
            }
        }
        if (!foundDuplicates) {
            System.out.println("All messages and algorithms are unique");
        }
    }
}
