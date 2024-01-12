package org.geekhub.repository;

import java.util.LinkedList;
import java.util.List;

public class MessagesInMemory implements MessagesRepository {

    private final List<String> messages = new LinkedList<>();

    @Override
    public void addMessage(String message) {
        messages.add(message);
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

    @Override
    public String getMessage(int index) {
        return messages.get(index);
    }

    @Override
    public int size() {
        return messages.size();
    }
}
