package org.geekhub.hw5;

import org.geekhub.hw5.exception.FileException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

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
        } catch (FileException | IOException e) {
            throw new FileException("Unable to create directories", e);
        }
    }

    public static void writeToFile(Path path, byte[] content) {
        try {
            Files.write(path, content);
        } catch (FileException | IOException e) {
            throw new FileException("Failed to write to file", e);
        }
    }

    public static void copyToFile(InputStream inputStream, Path path) {
        try {
            Files.write(path, inputStream.readAllBytes());
        } catch (FileException | IOException e) {
            throw new FileException("Failed to copy", e);
        }
    }

    public static void createFileIfNotExists(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (FileException | IOException e) {
                throw new FileException("Failed to create the file", e);
            }
        }
    }

    public static void deleteDirectories(String directory) {
        try {
            Files.walkFileTree(Path.of(directory), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(
                    Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (FileException | IOException e) {
            throw new FileException("Unable to delete directory", e);
        }
    }

    public static void deleteIfExists(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (FileException | IOException e) {
            throw new FileException("Failed to delete", e);
        }
    }
}
