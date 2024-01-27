package org.geekhub.repository;

import java.util.List;

public interface LogRepository {
    void addMessage(String message);

    List<String> loadHistory();

    void saveHistory();
}
