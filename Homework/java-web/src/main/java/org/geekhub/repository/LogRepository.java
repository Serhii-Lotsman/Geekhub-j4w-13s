package org.geekhub.repository;

import java.util.List;

public interface LogRepository {
    void addMessage(String message);
    List<String> getMessages();
    String getMessage(int index);
    int size();
    void loadHistory();
    String saveHistory();
}
