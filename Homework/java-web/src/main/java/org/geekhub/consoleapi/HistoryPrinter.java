package org.geekhub.consoleapi;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class HistoryPrinter {

    private static final String DEFAULT = "-------------------------------%s-------------------------------";
    private static final String COUNT_OF_USAGE = String.format(DEFAULT, "C-O-U-N-T--O-F--U-S-A-G-E");
    private static final String CURRENT_MESSAGE = String.format(DEFAULT, "C-U-R-R-E-N-T--M-E-S-S-A-G-E");
    private static final String HISTORY = String.format(DEFAULT, "H-I-S-T-O-R-Y");
    private static final String MESSAGES_BY_DATE = String.format(DEFAULT, "M-E-S-S-A-G-E-S--BY--D-A-T-E");
    private static final String UNIQUE_MESSAGE = String.format(DEFAULT, "U-N-I-Q-U-E--M-E-S-S-A-G-E");
    private static final String END_LINE = String.format(DEFAULT, "-------------------------");
    private static final String HISTORY_EMPTY = "History empty";
    private static final int INDEX_END_DATE = 10;

    public void printCurrentMessage(String message) {
        System.out.println(CURRENT_MESSAGE);
        System.out.println(message);
        System.out.println(END_LINE);
    }

    public void printCountOfUsage(Map<String, Integer> statistic) {
        System.out.println(COUNT_OF_USAGE);
        if (statistic.isEmpty()) {
            System.out.println(HISTORY_EMPTY);
        }
        statistic.forEach((algorithmType, count) ->
            System.out.printf("%s was used %s times%n", algorithmType, count));
        System.out.println(END_LINE);
    }

    public void printLoadedHistory(List<String> allHistory) {
        System.out.println(HISTORY);
        if (allHistory.isEmpty()) {
            System.out.println(HISTORY_EMPTY);
        }
        for (String message : allHistory) {
            System.out.println(message);
        }
        System.out.println(END_LINE);
    }

    public void printHistoryByDate(Map<String, List<String>> historyByDate) {
        System.out.println(MESSAGES_BY_DATE);
        if (historyByDate.isEmpty()) {
            System.out.println(HISTORY_EMPTY);
        }
        printMessageByDate(historyByDate);
        System.out.println(END_LINE);
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
