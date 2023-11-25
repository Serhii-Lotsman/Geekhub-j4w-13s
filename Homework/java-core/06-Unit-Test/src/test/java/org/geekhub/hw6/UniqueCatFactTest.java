package org.geekhub.hw6;

import org.geekhub.hw6.exception.CatFactException;
import org.geekhub.hw6.exception.FileException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

class UniqueCatFactTest {
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private static final Path TEMP_FILE_PATH = Path.of(TEMP_DIR, "catFactsTest.txt");

    private UniqueCatFact uniqueCatFact;
    @Mock
    private CatFactService catFactService;

    @BeforeEach
    void setUp() throws IOException {
        uniqueCatFact = new UniqueCatFact();
        Files.createFile(TEMP_FILE_PATH);
    }
    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TEMP_FILE_PATH);
    }

    @Test
    void createFileIfNotExists() {
        uniqueCatFact.createFileIfNotExists(TEMP_FILE_PATH);

        assertTrue(Files.exists(TEMP_FILE_PATH));
    }

    @Test
    void writeFactToFile() throws IOException{
        String fact = "The unique fact about cats";
        uniqueCatFact.writeFactToFile(TEMP_FILE_PATH, fact);

        assertEquals(fact, Files.readString(TEMP_FILE_PATH).trim());
    }

    @Test
    void setInterval() {
        long interval = 5;
        assertTimeout(ofSeconds(interval + 1), () -> uniqueCatFact.setInterval(interval));
    }

    @Test
    void checkUniqueCatFact_shouldThrowCatFactException_whenFetchingError() {

        assertThrows(NoSuchElementException.class, () -> uniqueCatFact.checkUniqueCatFact(catFactService));
    }
}
