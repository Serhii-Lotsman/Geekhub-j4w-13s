package org.geekhub.hw6;

import com.google.gson.Gson;
import org.apache.http.impl.client.CloseableHttpClient;
import org.geekhub.hw6.exception.CatFactException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UniqueCatFactTest {
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private static final Path TEMP_FILE_PATH = Path.of(TEMP_DIR, "catFactsTest.txt");

    private CatFactService catFactService;
    @Mock
    private CloseableHttpClient httpClient;

    @BeforeEach
    void setUp() {
        catFactService = new CatFactService(httpClient, new Gson());
    }

    @Test
    void checkUniqueCatFact_shouldThrowCatFactException_whenFailToFetchFact() {
        CatFactException thrown = assertThrows(CatFactException.class,
            () -> Files.writeString(TEMP_FILE_PATH, catFactService.getRandomCatFact()));

        assertEquals("Fail to fetch new cat fact", thrown.getMessage());
    }
}
