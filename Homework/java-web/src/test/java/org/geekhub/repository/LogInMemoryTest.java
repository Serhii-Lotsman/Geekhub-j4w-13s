package org.geekhub.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogInMemoryTest {

    private static final String TEST_MESSAGE = "Test log message";
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private static final Path TEMP_FILE_PATH = Path.of(TEMP_DIR, "test_logHistory.txt");

    private LogInMemory logInMemory;

    @BeforeEach
    void setUp() throws IOException {
        Files.createFile(TEMP_FILE_PATH);
        logInMemory = new LogInMemory(TEMP_FILE_PATH);
    }

    @Test
    void addMessage_shouldAddMessageToHistory_whenValidMessage() {
        logInMemory.addMessage(TEST_MESSAGE);
        logInMemory.saveHistory();
        List<String> history = logInMemory.loadHistory();

        assertTrue(history.contains(TEST_MESSAGE));
    }

    @Test
    void saveHistory_shouldSaveHistoryToFile_always() throws IOException {
        logInMemory.addMessage(TEST_MESSAGE);
        logInMemory.saveHistory();

        List<String> linesInFile = Files.readAllLines(TEMP_FILE_PATH);

        assertEquals(1, linesInFile.size());
        assertEquals(TEST_MESSAGE, linesInFile.get(0));
    }

    @Test
    void loadHistory_shouldLoadHistoryFromFile_whenNotEmptyFile() throws IOException {
        Files.write(TEMP_FILE_PATH, List.of(TEST_MESSAGE));

        List<String> history = logInMemory.loadHistory();

        assertEquals(1, history.size());
        assertEquals(TEST_MESSAGE, history.get(0));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TEMP_FILE_PATH);
    }
}

