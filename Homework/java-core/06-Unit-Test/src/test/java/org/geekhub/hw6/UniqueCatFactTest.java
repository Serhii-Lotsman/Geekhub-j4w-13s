package org.geekhub.hw6;

import com.google.gson.Gson;
import org.apache.http.impl.client.CloseableHttpClient;
import org.geekhub.hw6.exception.CatFactException;
import org.geekhub.hw6.exception.FileException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UniqueCatFactTest {
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private static final Path TEMP_FILE_PATH = Path.of(TEMP_DIR, "catFactsTest.txt");

    private UniqueCatFact uniqueCatFact;
    private CatFactService catFactService;
    @Mock
    private CloseableHttpClient httpClient;

    @BeforeEach
    void setUp() throws IOException {
        uniqueCatFact = new UniqueCatFact();
        catFactService = new CatFactService(httpClient, new Gson());
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
    void createFileIfNotExists_shouldThrowFileException_whenCannotCreateFile() {
        Path incorrectPath = Path.of("testFile", ".txt");
        FileException thrown = assertThrows(FileException.class, () -> uniqueCatFact.createFileIfNotExists(incorrectPath));

        assertEquals("Failed to create the file", thrown.getMessage());
    }

    @Test
    void writeFactToFile() throws IOException{
        String fact = "The unique fact about cats";
        uniqueCatFact.writeFactToFile(TEMP_FILE_PATH, fact);

        assertEquals(fact, Files.readString(TEMP_FILE_PATH).trim());
    }

    @Test
    void writeFactToFile_shouldThrowFileException_whenCannotWriteToFile() {
        String fact = "The unique fact about cats";
        Path incorrectPath = Path.of("testFile", ".txt");
        FileException thrown = assertThrows(FileException.class, () -> uniqueCatFact.writeFactToFile(incorrectPath, fact));

        assertEquals("Failed to write the fact", thrown.getMessage());
    }

    @Test
    void setInterval() {
        long interval = 5;

        assertTimeout(ofSeconds(interval + 1), () -> uniqueCatFact.setInterval(interval));
    }

    @Test
    void checkUniqueCatFact_shouldThrowCatFactException_whenFailToFetchFact() {
        CatFactException thrown = assertThrows(CatFactException.class,
            () -> Files.writeString(TEMP_FILE_PATH, catFactService.getRandomCatFact()));

        assertEquals("Fail to fetch new cat fact", thrown.getMessage());
    }
}
