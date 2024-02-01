package org.geekhub.repository;

import org.geekhub.model.Message;
import org.springframework.stereotype.Repository;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class EncryptedMessageRepositoryImpl implements EncryptedMessageRepository {

    @Override
    public void save(Message message) {
    }

    @Override
    public List<Message> findAll() {
        return null;
    }

    @Override
    public List<Message> findByAlgorithm(String algorithm) {
        return null;
    }

    @Override
    public List<Message> findByDate(DateTimeFormatter date) {
        return null;
    }

}
