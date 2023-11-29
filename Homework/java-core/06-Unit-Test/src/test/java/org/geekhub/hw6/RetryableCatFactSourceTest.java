package org.geekhub.hw6;

import com.google.gson.Gson;
import org.apache.http.impl.client.CloseableHttpClient;
import org.geekhub.hw6.exception.CatFactException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class RetryableCatFactSourceTest {
    private Set<String> catFactSet;
    CatFactService catFactService;
    @Mock
    private CloseableHttpClient httpClient;

    @BeforeEach
    void setUp() {
        catFactSet = new HashSet<>();
        catFactService = new CatFactService(httpClient, new Gson());
    }

    @Test
    void retryCatFact() throws CatFactException {
        CatFactService catFactService = mock(CatFactService.class);
        when(catFactService.getRandomCatFact()).thenReturn("Unique cat fact");
        var fetchFact = RetryableCatFactSource.retryCatFact(catFactService);
        catFactSet.add(fetchFact);
        String setRes = "";
        for (String element : catFactSet) {
            setRes = element;
        }

        assertEquals(fetchFact, setRes);
    }

    @AfterEach
    void tearDown() {
        catFactSet.clear();
    }
}
