package org.geekhub.repository;

import java.util.List;

public interface CipherRepository {
    String addMessage(String message);

    List<String> getAllMessages();


}
