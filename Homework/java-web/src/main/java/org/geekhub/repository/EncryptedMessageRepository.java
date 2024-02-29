package org.geekhub.repository;

import org.geekhub.model.Message;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface EncryptedMessageRepository {
    void saveMessage(Message message);

    Optional<Message> findMessageById(long id);

    List<Message> findAll();

    List<Message> findByAlgorithm(String algorithm);

    List<Message> findByDate(OffsetDateTime dateFrom, OffsetDateTime dateTo);

    List<Message> getPaginateHistory(int pageNum, int pageSize);
}
