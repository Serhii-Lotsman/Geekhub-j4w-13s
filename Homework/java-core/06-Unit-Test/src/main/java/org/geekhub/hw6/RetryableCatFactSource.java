package org.geekhub.hw6;

import org.geekhub.hw6.exception.CatFactException;

import java.util.HashSet;
import java.util.Set;

public class RetryableCatFactSource {
    private RetryableCatFactSource(){}

    private static final Set<String> catFactSet = new HashSet<>();

    static String retryCatFact(CatFactService catFactService) throws CatFactException {
        int retryCount = 5; // prevent default test-checking env var: Integer.parseInt(System.getenv("VAR"))
        for (int i = 0; i < retryCount; i++) {
            try {
                var response = catFactService.getRandomCatFact();
                if (!catFactSet.contains(response)) {
                    catFactSet.add(response);
                    return response;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FileService.writeFactToFile(UniqueCatFact.FILE_PATH, "I don't know any new facts");
        throw new CatFactException("Fail to find new cat fact");
    }
}
