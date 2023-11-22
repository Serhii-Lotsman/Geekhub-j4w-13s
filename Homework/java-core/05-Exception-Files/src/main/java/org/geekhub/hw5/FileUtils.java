package org.geekhub.hw5;

import org.geekhub.hw5.exception.FileException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class FileUtils {

    private FileUtils() {
    }

    public static List<String> readAllLines(String file) {
        List<String> strings;
        try {
            strings = Files.readAllLines(Path.of(file));
        } catch (FileException | IOException e) {
            throw new FileException("File doesn't exist", e);
        }
        return strings;
    }

    public static void createDirectories(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(Path path, byte[] content) {
        try {
            Files.write(path, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyToFile(InputStream inputStream, Path path) {
        try {
            Files.write(path, inputStream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFileIfNotExists(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteDirectories(String directory) {
        try (Stream<Path> pathStream = Files.walk(Path.of(directory))){
            final List<Path> pathsToDelete = pathStream.sorted(Collections.reverseOrder()).toList();
            for(Path path : pathsToDelete) {
                Files.deleteIfExists(path);
            }
        } catch (FileException | IOException e) {
            throw new FileException("Unable to delete directory", e);
        }
    }

    public static void deleteIfExists(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
