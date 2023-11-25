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

    private static final String FETCH_PATH = System.getProperty("user.dir");
    private static final Path FILE_PATH = Path.of(FETCH_PATH + Path.of("/catFacts.txt"));

    public void createFileIfNotExists(Path filePath) {
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                throw new FileException("Failed to create the file", e);
            }
        }
    }

    public void writeFactToFile(Path path, String catFact) {
        try {
            Files.writeString(path, catFact + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new FileException("Failed to write the fact", e);
        }
    }

    public void setInterval(long interval) throws InterruptedException {
        Thread.sleep( interval * 1000);
    }

    public void checkUniqueCatFact(CatFactService catFactService) throws CatFactException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of interval in seconds: ");
        long interval = scanner.nextLong();
        System.out.println("Fetching facts...");

        createFileIfNotExists(FILE_PATH);

        try {
            while (true) {
                String catFact = catFactService.getRandomCatFact();
                if (retries == 0) {
                    catFact = "I don't know any new facts";
                    writeFactToFile(FILE_PATH, catFact);
                    System.out.println("Fetching complete");
                    break;
                } else if (!Files.readAllLines(FILE_PATH).contains(catFact) && !catFact.isEmpty()) {
                    writeFactToFile(FILE_PATH, catFact);
                    retries = 5;
                    setInterval(interval);
                } else retries--;
            }
        } catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
            throw new CatFactException("Fail to fetch new cat fact", e);
        }
    }
}
