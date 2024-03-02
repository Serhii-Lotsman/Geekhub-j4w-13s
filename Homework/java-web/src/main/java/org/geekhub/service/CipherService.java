package org.geekhub.service;

import cipherAlgorithm.*;
import org.geekhub.exception.EncryptException;
import org.geekhub.exception.UserException;
import org.geekhub.model.Algorithm;
import org.geekhub.model.Operation;
import org.geekhub.model.Message;
import org.geekhub.repository.EncryptedMessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CipherService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private final EncryptedMessageRepository repository;
    private final UserService userService;
    private final Map<Map<Algorithm, Operation>, Function<String, String>> ciphers;

    public CipherService(
        @Value("${cipher.caesar.key}") int caesarKey,
        @Value("${cipher.vigenere.key}") String vigenereKey,
        EncryptedMessageRepository repository,
        UserService userService
    ) {
        this.repository = repository;
        this.userService = userService;
        Cipher caesarCipher = new CaesarCipher(caesarKey);
        Cipher vigenereCipher = new VigenereCipher(vigenereKey);
        this.ciphers = Map.of(
            Map.of(Algorithm.CAESAR, Operation.ENCRYPT), caesarCipher::encrypt,
            Map.of(Algorithm.VIGENERE, Operation.ENCRYPT), vigenereCipher::encrypt,
            Map.of(Algorithm.CAESAR, Operation.DECRYPT), caesarCipher::decrypt,
            Map.of(Algorithm.VIGENERE, Operation.DECRYPT), vigenereCipher::decrypt
        );
    }

    public Message saveMessage(long userId, String originalMessage, String algorithm, String operation) {
        if (!userService.isUserExist(userId)) {
            throw new UserException("User with id " + userId + " not exists");
        }
        Algorithm messageAlgorithm = algorithm.equalsIgnoreCase(Algorithm.CAESAR.name())
            ? Algorithm.CAESAR
            : Algorithm.VIGENERE;
        Operation messageOperation = operation.equalsIgnoreCase(Operation.ENCRYPT.name())
            ? Operation.ENCRYPT
            : Operation.DECRYPT;
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        String encryptedMessage = ciphers.get(Map.of(messageAlgorithm, messageOperation)).apply(originalMessage);

        Message message = new Message(
            null,
            userId,
            originalMessage,
            encryptedMessage,
            messageAlgorithm.name(),
            messageOperation.name(),
            offsetDateTime.format(FORMATTER)
        );
        repository.saveMessage(message);
        return message;
    }

    public List<Message> getMessagesByAlgorithm(String param) {
        List<Message> messages;
        switch (param) {
            case "caesar" -> messages = repository.findByAlgorithm("CAESAR");
            case "vigenere" -> messages = repository.findByAlgorithm("VIGENERE");
            default -> messages = getAllMessages();
        }
        return messages;
    }

    public List<Message> getAllMessages() {
        return repository.findAll();
    }

    public Message getMessageById(long id) {
        return repository.findMessageById(id)
            .orElseThrow(() -> new EncryptException("User with id " + id + " not found"));
    }

    public List<Message> getMessageByDate(String inputDateFrom, String inputDateTo) {
        OffsetDateTime dateFrom = parse(inputDateFrom);
        OffsetDateTime dateTo = parse(inputDateTo);

        if (dateFrom == null && dateTo == null) {
            throw new EncryptException(
                "Please indicate at least one of the dates"
            );
        }
        return repository.findByDate(dateFrom, dateTo);
    }

    private OffsetDateTime parse(String date) {
        OffsetDateTime dateTime = date.contains("T")
            ? OffsetDateTime.of(LocalDateTime.parse(date, TIME_FORMATTER), ZoneOffset.UTC)
            : OffsetDateTime.of(LocalDateTime.parse(date, FORMATTER), ZoneOffset.UTC);
        return date.isEmpty()
            ? null
            : dateTime;
    }

    public List<Message> getAllHistory(int pageNum, int pageSize) {
        if (pageNum < 1 || pageSize < 1) {
            throw new IllegalArgumentException("Page number and page size must be greater than 0");
        }
        return repository.getPaginateHistory(pageNum, pageSize);
    }

    public List<Map<String, Long>> getStatistics() {
        List<Map<String, Long>> statisticList = new ArrayList<>();
        statisticList.add(getCountOfUsage());
        statisticList.add(getUniqueMessages());
        return statisticList;
    }

    private Map<String, Long> getCountOfUsage() {
        List<Message> messages = repository.findAll();

        return messages.stream()
            .map(Message::getAlgorithm)
            .map(String::toLowerCase)
            .collect(Collectors.groupingBy(algorithmName -> algorithmName, Collectors.counting()))
            .entrySet().stream()
            .collect(Collectors.toMap(
                entry -> String.format("Usage of %s algorithm", entry.getKey()),
                Map.Entry::getValue
            ));
    }

    private Map<String, Long> getUniqueMessages() {
        List<Message> messages = repository.findAll();
        return getMapUniqueMessages(messages);
    }

    private Map<String, Long> getMapUniqueMessages(List<Message> messages) {
        return messages.stream()
            .map(message ->
                String.format("'%s' was %sed via %s algorithm into '%s'",
                    message.getOriginalMessage(),
                    message.getOperation().toLowerCase(),
                    message.getAlgorithm().toLowerCase(),
                    message.getEncryptedMessage()
                ))
            .collect(Collectors.groupingBy(message -> message, Collectors.counting()))
            .entrySet().stream()
            .filter(entry -> entry.getValue() >= 2)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
