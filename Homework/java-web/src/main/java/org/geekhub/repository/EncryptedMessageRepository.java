package org.geekhub.repository;

import org.geekhub.model.Message;

import java.time.format.DateTimeFormatter;
import java.util.List;

public interface EncryptedMessageRepository {
    void save(Message message);

    List<Message> findAll();

    List<Message> findByAlgorithm(String algorithm);

    List<Message> findByDate(DateTimeFormatter date);
}
