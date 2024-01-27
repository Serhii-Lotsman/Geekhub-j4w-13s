package org.geekhub.repository;

import org.geekhub.exception.EncryptException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LogInMemory implements LogRepository{

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
                throw new EncryptException("Error creating file: " + e.getMessage());
            }
        }
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
            throw new EncryptException(e.getMessage());
        }
        return lastMessage;
    }
}
