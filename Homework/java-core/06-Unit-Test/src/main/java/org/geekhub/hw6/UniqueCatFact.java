package org.geekhub.hw6;

import org.geekhub.hw6.exception.CatFactException;
import org.geekhub.hw6.exception.FileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class UniqueCatFact {
    private int retries = 5;

    String fetchPath = System.getProperty("user.dir");
    Path filePath = Path.of(fetchPath + Path.of("/resources/catFacts.txt"));

    public void createFileIfNotExist(Path filePath) {
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                throw new FileException("Failed to create the file", e);
            }
        }
    }

    public void writeFactToFile(String catFact) throws CatFactException {
        try {
            Files.writeString(filePath, catFact + "\n", StandardOpenOption.APPEND);
        } catch (Exception e) {
            throw new CatFactException("Fail to write the fact", e);
        }
    }

    public void setRetries(long interval) throws InterruptedException {
        Thread.sleep( interval * 1000);
    }

    public void checkUniqueCatFact(CatFactService catFactService) throws CatFactException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of interval in seconds: ");
        long interval = scanner.nextLong();
        System.out.println("Fetching facts...");
        createFileIfNotExist(filePath);
        try {
            while (retries != 0) {
                String catFact = catFactService.getRandomCatFact();
                if (!Files.readAllLines(filePath).contains(catFact) && !catFact.isEmpty()) {
                    writeFactToFile(catFact);
                    retries = 5;
                    setRetries(interval);
                } else retries--;
            }
            Files.writeString(filePath, "I don't know any new facts", StandardOpenOption.APPEND);
            System.out.println("Fetching complete");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            throw new CatFactException("Fail to write the fact", e);
        }
    }
}
