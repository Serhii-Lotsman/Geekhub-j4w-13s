package org.geekhub.repository;

import org.geekhub.exception.FileException;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LogInMemory implements LogRepository {

    private final List<String> history;
    private Path historyFilePath;


    public LogInMemory() {
        this.history = new ArrayList<>();
        createFile();
    }

    private void createFile() {
        String historyFile = "log_history.txt";
        Path resourcesPath = Path.of("Homework/java-web/src/main/resources");
        historyFilePath = resourcesPath.resolve(historyFile);

        File file = historyFilePath.toFile();

        if (!file.exists()) {
            try {
                Files.createFile(historyFilePath);
            } catch (IOException e) {
                throw new FileException("Error creating file: " + e.getMessage());
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
            List<String> messageHistory = Files.readAllLines(historyFilePath);
            history.clear();
            return new ArrayList<>(messageHistory);
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }

    @Override
    public void saveHistory() {
        try {
            Files.write(historyFilePath, history, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            history.clear();
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }
}
