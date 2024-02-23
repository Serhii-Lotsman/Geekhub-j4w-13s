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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CipherService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private final EncryptedMessageRepository repository;
    private final long userId;
    private final UserService userService;
    private final Map<Map<Algorithm, Operation>, Function<String, String>> ciphers;

    public CipherService(
            @Value("${user.id}") long userId,
            @Value("${cipher.caesar.key}") int caesarKey,
            @Value("${cipher.vigenere.key}") String vigenereKey,
            EncryptedMessageRepository repository,
            UserService userService
    ) {
        this.userId = userId;
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

    public Message saveMessage(String originalMessage, String algorithm, String operation) {
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

    public List<Message> getMessagesByDateAndAlgorithm(String param, String dateFrom, String dateTo) {
        List<Message> messages;
        if (dateFrom != null && dateTo != null) {
            messages = getMessageByDate(dateFrom, dateTo);
        } else {
            switch (param) {
                case "caesar" -> messages = repository.findByAlgorithm("CAESAR");
                case "vigenere" -> messages = repository.findByAlgorithm("VIGENERE");
                default -> messages = repository.findAll();
            }
        }
        return messages;
    }

    private List<Message> getMessageByDate(String inputDateFrom, String inputDateTo) {
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
        return date.isEmpty()
            ? null
            : date.contains("T")
            ? OffsetDateTime.of(LocalDateTime.parse(date, TIME_FORMATTER), ZoneOffset.UTC)
            : OffsetDateTime.of(LocalDateTime.parse(date, FORMATTER), ZoneOffset.UTC);
    }

    public List<Message> getAllHistory(int pageNum, int pageSize) {
        if (pageNum < 1 || pageSize < 1) {
            throw new IllegalArgumentException("Page number and page size must be greater than 0");
        }
        return repository.getPaginateHistory(pageNum, pageSize);
    }

    public Map<String, Integer> getCountOfUsage() {
        List<Message> messages = repository.findAll();

        return messages.stream()
            .map(Message::getAlgorithm)
            .collect(HashMap::new, (map, algorithmName) ->
                map.merge(algorithmName, 1, Integer::sum), HashMap::putAll);
    }

    public Map<String,Long> getUniqueMessages() {
        List<Message> messages = repository.findAll();
        return getMapUniqueMessages(messages);
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
}