package org.geekhub.repository;

import org.geekhub.annotation.Injectable;
import org.geekhub.service.AppConfig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LogInMemory implements LogRepository{

    private final List<String> history;
    @Injectable(property = "path.file")
    private final Path resourcesPath;
    private final Path historyFilePath;


    public LogInMemory() {
        AppConfig property = new AppConfig();
        property.loadProperties(LogInMemory.class);
        this.resourcesPath = Paths.get((String) property.getProperty());
        String historyFile = "log_history.txt";
        this.historyFilePath = resourcesPath.resolve(historyFile);
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
    public void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(historyFilePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String message : history) {
            System.out.println(message);
        }
        history.clear();
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
            e.printStackTrace();
        }
        return lastMessage;
    }
}
