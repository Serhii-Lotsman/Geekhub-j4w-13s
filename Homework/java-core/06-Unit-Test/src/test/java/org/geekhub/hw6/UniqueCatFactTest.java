package org.geekhub.hw6;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

class UniqueCatFactTest {
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private static final Path TEMP_FILE_PATH = Path.of(TEMP_DIR, "catFactsTest.txt");

    @BeforeEach
    void setUp() throws IOException {
        Files.createFile(TEMP_FILE_PATH);
    }
    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TEMP_FILE_PATH);
    }

    @Test
    void createFileIfNotExists() {
        UniqueCatFact.createFileIfNotExists(TEMP_FILE_PATH);

        assertTrue(Files.exists(TEMP_FILE_PATH));
    }

    @Test
    void writeFactToFile() throws IOException{
        String fact = "The unique fact about cats";
        UniqueCatFact.writeFactToFile(TEMP_FILE_PATH, fact);

        assertEquals(fact, Files.readString(TEMP_FILE_PATH).trim());
    }

    @Test
    void setInterval() {
        long interval = 5;
        assertTimeout(ofSeconds(interval + 1), () -> UniqueCatFact.setInterval(interval));
    }

    @Test
    void checkUniqueCatFact() {

    }
}
