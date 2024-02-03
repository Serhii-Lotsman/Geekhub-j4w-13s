package org.geekhub.consoleapi;

import org.geekhub.model.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class HistoryPrinter {
    private static final String HISTORY_EMPTY = "History empty";
    private static final int INDEX_END_DATE = 10;

    public void printCurrentMessage(Message message) {
        String messageInfo = String.format(
            "%s - Message '%s' was encrypted via %s into '%s'",
            message.getDate(),
            message.getOriginalMessage(),
            message.getAlgorithm(),
            message.getEncryptedMessage()
        );
        System.out.println(messageInfo);
    }

    public void printCountOfUsage(Map<String, Integer> statistic) {
        if (statistic.isEmpty()) {
            System.out.println(HISTORY_EMPTY);
        }
        statistic.forEach((algorithmType, count) ->
            System.out.printf("%s was used %s times%n", algorithmType, count));
    }

    public void printLoadedHistory(List<Message> allHistory) {
        if (allHistory.isEmpty()) {
            System.out.println(HISTORY_EMPTY);
        }
        for (Message message : allHistory) {
            printCurrentMessage(message);
        }
    }

    public void printHistoryByDate(Map<String, List<String>> historyByDate) {
        if (historyByDate.isEmpty()) {
            System.out.println(HISTORY_EMPTY);
        }
        printMessageByDate(historyByDate);
    }

    private static void printMessageByDate(Map<String, List<String>> historyByDate) {
        historyByDate.forEach((date, dateMessages) -> {
            List<String> substrings = dateMessages.stream()
                .map(message -> message.substring(INDEX_END_DATE + 1))
                .toList();

            System.out.printf("[%s] %n%s %n", date, String.join("\n", substrings));
        });
    }

    public void printUniqueMessages(Map<String, Long> uniqueMessages) {
        boolean isDuplicateMessage = false;
        int uniqueCount = 1;

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
    }
}
