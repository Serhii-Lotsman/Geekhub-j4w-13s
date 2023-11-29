package org.geekhub.hw6;

import org.geekhub.hw6.exception.FileException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private static final Path TEMP_FILE_PATH = Path.of(TEMP_DIR, "catFactsTest.txt");

    @BeforeEach
    void setUp() throws IOException {
        Files.createFile(TEMP_FILE_PATH);
    }

    @Test
    void createFileIfNotExists() {
        FileService.createFileIfNotExists(TEMP_FILE_PATH);

        assertTrue(Files.exists(TEMP_FILE_PATH));
    }

    @Test
    void createFileIfNotExists_shouldThrowFileException_whenCannotCreateFile() {
        Path incorrectPath = Path.of("testFile", ".txt");
        FileException thrown = assertThrows(FileException.class, () -> FileService.createFileIfNotExists(incorrectPath));

        assertEquals("Failed to create the file", thrown.getMessage());
    }

    @Test
    void writeFactToFile() throws IOException{
        String fact = "The unique fact about cats";
        FileService.writeFactToFile(TEMP_FILE_PATH, fact);

        assertEquals(fact, Files.readString(TEMP_FILE_PATH).trim());
    }

    @Test
    void writeFactToFile_shouldThrowFileException_whenCannotWriteToFile() {
        String fact = "The unique fact about cats";
        Path incorrectPath = Path.of("testFile", ".txt");
        FileException thrown = assertThrows(FileException.class, () -> FileService.writeFactToFile(incorrectPath, fact));

        assertEquals("Failed to write the fact", thrown.getMessage());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TEMP_FILE_PATH);
    }
}
