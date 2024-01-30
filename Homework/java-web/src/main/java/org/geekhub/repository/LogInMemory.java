package org.geekhub.repository;

import org.geekhub.exception.FileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LogInMemory implements LogRepository {

    private final List<String> history;
    private final Path historyFilePath;


    public LogInMemory(@Value("${path.to.file}") Path historyFilePath) {
        this.history = new ArrayList<>();
        this.historyFilePath = historyFilePath;
    }

    public void createFileIfNotExists() {
        if (!Files.exists(historyFilePath)) {
            try {
                Files.createFile(historyFilePath);
            } catch (IOException e) {
                throw new FileException("Failed to create the file");
            }
        }
    }

    @Override
    public void addMessage(String message) {
        history.add(message);
    }

    @Override
    public List<String> loadHistory() {
        try {
            return Files.readAllLines(historyFilePath)
                .stream()
                .toList();
        } catch (IOException e) {
            throw new FileException("Failed to load the file");
        }
    }

    @Override
    public void saveHistory() {
        try {
            Files.write(historyFilePath, history, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }
}
