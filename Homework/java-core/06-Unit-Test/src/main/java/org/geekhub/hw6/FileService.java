package org.geekhub.hw6;

import org.geekhub.hw6.exception.FileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileService {
    private FileService() {
    }

    static void createFileIfNotExists(Path filePath) {
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                throw new FileException("Failed to create the file", e);
            }
        }
    }

    static void writeFactToFile(Path path, String catFact) {
        try {
            Files.writeString(path, catFact + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new FileException("Failed to write the fact", e);
        }
    }
}
