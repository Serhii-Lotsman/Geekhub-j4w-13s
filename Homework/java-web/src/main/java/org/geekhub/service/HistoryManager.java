package org.geekhub.service;

import org.geekhub.consoleapi.HistoryPrinter;
import org.geekhub.model.Message;
import org.geekhub.repository.EncryptedMessageRepository;
import org.geekhub.repository.LogRepository;
import org.geekhub.service.cipher.Cipher;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final EncryptedMessageRepository repository;
    private final long userId;
    public HistoryManager(@Value("${user.id}") long userId,
                          HistoryPrinter historyPrinter,
                          LogRepository logRepository,
                          EncryptedMessageRepository repository,
                          @Qualifier("caesarCipher") Cipher cipher) {
        this.userId = userId;
        this.logRepository = logRepository;
        this.historyPrinter = historyPrinter;
        this.dateTime = OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.repository = repository;
        this.cipher = cipher;
    }


    public void saveMessage(String originalMessage, String algorithm) {
        String encryptedMessage = cipher.encrypt(originalMessage);

        Message message = new Message(
            userId,
            originalMessage,
            encryptedMessage,
            algorithm,
            dateTime.format(formatter)
            );

        repository.saveMessage(message);
        historyPrinter.printCurrentMessage(message);
    }

    public void getAllHistory() {
        List<Message> allHistory = repository.findAll();
        historyPrinter.printLoadedHistory(allHistory);
    }

    public void getCountOfUsage() {
        List<Message> messages = repository.findAll();
        Map<String, Integer> statistic = messages.stream()
            .map(Message::getAlgorithm)
            .collect(HashMap::new, (map, algorithmName) ->
                map.merge(algorithmName, 1, Integer::sum), HashMap::putAll);

        historyPrinter.printCountOfUsage(statistic);
    }

    public void getUniqueMessages() {
        List<Message> messages = repository.findAll();
        Map<String, Long> uniqueMessages = getMapUniqueMessages(messages);

        historyPrinter.printUniqueMessages(uniqueMessages);
    }

    private Map<String, Long> getMapUniqueMessages(List<Message> messages) {
        return messages.stream()
            .map(message ->
                String.format("'%s' was encrypted via %s into '%s'",
                message.getOriginalMessage(),
                message.getAlgorithm(),
                message.getEncryptedMessage()
            ))
            .collect(Collectors.groupingBy(message ->
                message, Collectors.counting()));
    }

    public void getMessageByDate(String specificDate) {
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
