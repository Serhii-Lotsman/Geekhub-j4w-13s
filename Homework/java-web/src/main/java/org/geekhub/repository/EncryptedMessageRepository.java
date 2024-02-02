package org.geekhub.repository;

import org.geekhub.model.Message;

import java.time.OffsetDateTime;
import java.util.List;

public interface EncryptedMessageRepository {
    void saveMessage(Message message);

    List<Message> findAll();

    List<Message> findByAlgorithm(String algorithm);

    List<Message> findByDate(OffsetDateTime date);
}
