package org.geekhub.hw5;

import org.geekhub.hw5.exception.FileException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtils {

    private FileUtils() {
    }

    public static List<String> readAllLines(String file) {
        List<String> strings;
        try {
            strings = Files.readAllLines(Path.of(file));
        } catch (FileException | IOException err) {
            throw new FileException("File doesn't exist", err);
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
       //TODO-7 write code here AND REMOVE THIS MESSAGE
    }

    public static void deleteDirectories(String directory) {
      //TODO-8 write code here AND REMOVE THIS MESSAGE
    }

    public static void deleteIfExists(Path path) {
      //TODO-9 write code here AND REMOVE THIS MESSAGE
    }
}
