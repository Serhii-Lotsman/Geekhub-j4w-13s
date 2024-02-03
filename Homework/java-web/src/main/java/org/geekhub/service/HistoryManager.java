package org.geekhub.service;

import org.geekhub.consoleapi.HistoryPrinter;
import org.geekhub.model.Algorithm;
import org.geekhub.model.Message;
import org.geekhub.repository.EncryptedMessageRepository;
import org.geekhub.service.cipher.Cipher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class HistoryManager {

    private static final DateTimeFormatter SAVE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter GET_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private final HistoryPrinter historyPrinter;
    private final OffsetDateTime dateTime;
    private final EncryptedMessageRepository repository;
    private final long userId;
    private final Map<Algorithm, Function<String, String>> ciphers;
    public HistoryManager(
        @Value("${user.id}") long userId,
        HistoryPrinter historyPrinter,
        EncryptedMessageRepository repository,
        @Qualifier("caesarCipher") Cipher caesarCipher,
        @Qualifier("vigenereCipher") Cipher vigenereCipher
    ) {
        this.userId = userId;
        this.historyPrinter = historyPrinter;
        this.dateTime = OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        this.repository = repository;
        this.ciphers = Map.of(
            Algorithm.CAESAR, caesarCipher::encrypt,
            Algorithm.VIGENERE, vigenereCipher::encrypt
        );
    }


    public void saveMessage(String originalMessage, Algorithm algorithm) {
        String encryptedMessage = ciphers.get(algorithm).apply(originalMessage);

        Message message = new Message(
            userId,
            originalMessage,
            encryptedMessage,
            algorithm.name(),
            dateTime.format(SAVE_FORMATTER)
        );

        repository.saveMessage(message);
        historyPrinter.printCurrentMessage(message);
    }

    public void getAllHistory() {
        List<Message> allHistory = repository.findAll();
        historyPrinter.printMessages(allHistory);
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

    public void getMessageByAlgorithm(String algorithm) {
        List<Message> messages = repository.findByAlgorithm(algorithm);

        historyPrinter.printMessages(messages);
    }

    public void getMessageByDate(String inputDateFrom, String inputDateTo) {
        OffsetDateTime dateFrom = parse(inputDateFrom);
        OffsetDateTime dateTo = parse(inputDateTo);
        List<Message> messages = repository.findByDate(dateFrom, dateTo);

        historyPrinter.printMessages(messages);
    }

    private OffsetDateTime parse(String date) {
        return date.isEmpty()
            ? null
            : OffsetDateTime.of(LocalDateTime.parse(date, GET_FORMATTER), ZoneOffset.UTC);
    }
}
