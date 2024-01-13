package org.geekhub.repository;

public interface LogRepository {
    void addMessage(String message);
    void loadHistory();
    String saveHistory();
}
