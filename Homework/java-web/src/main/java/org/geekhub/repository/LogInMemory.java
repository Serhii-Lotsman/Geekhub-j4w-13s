package org.geekhub.repository;

import org.geekhub.exception.EncryptException;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LogInMemory implements LogRepository{

    private final List<String> history;
    @Value("${path.file}")
    private Path resourcesPath;
    private final Path historyFilePath;


    public LogInMemory() {
        String historyFile = "log_history.txt";
        this.historyFilePath = resourcesPath.resolve(historyFile);
        this.history = new ArrayList<>();
    }

    @Override
    public void addMessage(String message) {
        history.add(message);
    }

    @Override
    public List<String> loadHistory() {
        List<String> messageHistory;
        try (BufferedReader reader = new BufferedReader(new FileReader(historyFilePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
            messageHistory = new ArrayList<>(history);
            if (history.isEmpty()) {
                throw new EncryptException("History is empty");
            }
        } catch (IOException e) {
            throw new EncryptException(e.getMessage());
        }
        history.clear();
        return messageHistory;
    }

    @Override
    public String saveHistory() {
        String lastMessage = "";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(historyFilePath.toFile(), true))) {
            for (String entry : history) {
                writer.write(entry);
                writer.newLine();
                lastMessage = entry;
            }
            history.clear();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return lastMessage;
    }
}
