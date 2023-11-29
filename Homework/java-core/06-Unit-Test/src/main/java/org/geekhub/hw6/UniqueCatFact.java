package org.geekhub.hw6;

import org.geekhub.hw6.exception.CatFactException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UniqueCatFact {
    private static final String FETCH_PATH = System.getProperty("user.dir");
    static final Path FILE_PATH = Path.of(FETCH_PATH + Path.of("/catFacts.txt"));

    void checkUniqueCatFact(CatFactService catFactService) throws CatFactException {

        FileService.createFileIfNotExists(FILE_PATH);

        try {
            while (true) {
                String catFact = RetryableCatFactSource.retryCatFact(catFactService);
                if (!Files.readAllLines(FILE_PATH).contains(catFact) && !catFact.isEmpty()) {
                    FileService.writeFactToFile(FILE_PATH, catFact);
                } else break;
                Thread.sleep(1000);
            }
        } catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
            throw new CatFactException("Fail to fetch new cat fact", e);
        }
    }
}
