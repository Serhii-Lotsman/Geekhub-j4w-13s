package org.geekhub.repository;

import java.util.List;

public interface LogRepository {
    void createFileIfNotExists();
    void addMessage(String message);

    List<String> loadHistory();

    void saveHistory();
}
