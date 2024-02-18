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

    public Message saveMessage(String originalMessage, Algorithm algorithm, CipherOperation operation) {
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        String status = originalMessage != null ? "successfully" : "failed";
        String encryptedMessage = ciphers.get(Map.of(algorithm, operation)).apply(originalMessage);

        Message message = new Message(
            userId,
            originalMessage,
            encryptedMessage,
            algorithm.name(),
            operation.name(),
            offsetDateTime.format(FORMATTER),
            status
        );

        repository.saveMessage(message);
        return message;
    }

    public List<Message> getAllHistory() {
        return repository.findAll();
    }

    public List<Message> getMessageByAlgorithm(String algorithm) {
        return repository.findByAlgorithm(algorithm);
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
        if (date.contains("T")) {
            date = date.replace('T', ' ') + ":00";
        }
        return date.isEmpty()
            ? null
            : OffsetDateTime.of(LocalDateTime.parse(date, FORMATTER), ZoneOffset.UTC);
    }
}
