package org.geekhub.service;

import cipherAlgorithm.*;
import org.geekhub.exception.EncryptException;
import org.geekhub.model.Algorithm;
import org.geekhub.model.CipherOperation;
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
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class CipherService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private final EncryptedMessageRepository repository;
    private final long userId;
    private final Map<Map<Algorithm, CipherOperation>, Function<String, String>> ciphers;

    public CipherService(
            @Value("${user.id}") long userId,
            @Value("${cipher.caesar.key}") int caesarKey,
            @Value("${cipher.vigenere.key}") String vigenereKey,
            EncryptedMessageRepository repository
    ) {
        this.userId = userId;
        this.repository = repository;
        Cipher caesarCipher = new CaesarCipher(caesarKey);
        Cipher vigenereCipher = new VigenereCipher(vigenereKey);
        this.ciphers = Map.of(
            Map.of(Algorithm.CAESAR, CipherOperation.ENCRYPT), caesarCipher::encrypt,
            Map.of(Algorithm.VIGENERE, CipherOperation.ENCRYPT), vigenereCipher::encrypt,
            Map.of(Algorithm.CAESAR, CipherOperation.DECRYPT), caesarCipher::decrypt,
            Map.of(Algorithm.VIGENERE, CipherOperation.DECRYPT), vigenereCipher::decrypt
        );
    }

    public Message saveMessage(String originalMessage, String algorithm, String operation) {
        Algorithm messageAlgorithm = algorithm.equalsIgnoreCase(Algorithm.CAESAR.name())
            ? Algorithm.CAESAR
            : Algorithm.VIGENERE;
        CipherOperation messageOperation = operation.equalsIgnoreCase(CipherOperation.ENCRYPT.name())
            ? CipherOperation.ENCRYPT
            : CipherOperation.DECRYPT;
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        String status = originalMessage != null ? "successfully" : "failed";
        String encryptedMessage = ciphers.get(Map.of(messageAlgorithm, messageOperation)).apply(originalMessage);

        Message message = new Message(
            userId,
            originalMessage,
            encryptedMessage,
            messageAlgorithm.name(),
            messageOperation.name(),
            offsetDateTime.format(FORMATTER),
            status
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
}
