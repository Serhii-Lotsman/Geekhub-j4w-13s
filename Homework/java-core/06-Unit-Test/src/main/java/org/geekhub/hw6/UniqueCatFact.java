package org.geekhub.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class UniqueCatFact {
    private int retries = 5;

    String fetchPath = System.getProperty("user.dir");
    Path filePath = Path.of(fetchPath + Path.of("/resources/catFacts.txt"));

    public void createFileIfNotExist(Path filePath) {
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeFactToFile(String catFact) throws CatFactException{
        try {
            Files.writeString(filePath, catFact + "\n", StandardOpenOption.APPEND);
        } catch (Exception e) {
            throw new CatFactException("Fail to write the fact", e);
        }
    }

    public void checkUniqueCatFact(CatFactService catFactService) throws CatFactException {
        createFileIfNotExist(filePath);
        try {
            while (retries != 0) {
                String catFact = catFactService.getRandomCatFact();
                if (Files.size(filePath) == 0) {
                    writeFactToFile(catFact);
                } else if (!Files.readAllLines(filePath).contains(catFact)) {
                    writeFactToFile(catFact);
                    retries = 5;
                    Thread.sleep(1000);
                } else retries--;
            }
            Files.writeString(filePath, "I don't know any new facts", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
