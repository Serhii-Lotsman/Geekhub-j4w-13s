package org.geekhub.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogInMemory implements LogRepository{

    private final List<String> history;
    private static final String HISTORY_FILE = "log_history.txt";

    public LogInMemory() {
        this.history = new ArrayList<>();
    }

    @Override
    public void addMessage(String message) {
        history.add(message);
    }

    @Override
    public List<String> getMessages() {
        return new ArrayList<>(history);
    }

    @Override
    public String getMessage(int index) {
        return history.get(index);
    }

    @Override
    public int size() {
        return history.size();
    }

    @Override
    public List<String> loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return history;
    }

    @Override
    public void saveHistory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            for (String entry : history) {
                writer.write(entry);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
