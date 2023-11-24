package org.geekhub.hw6;

import com.google.gson.Gson;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Main {
    public static void main(String[] args) throws CatFactException {
        var httpClient = HttpClientBuilder.create().build();
        var catFactService = new CatFactService(httpClient, new Gson());
        int retries = 5;
        String fetchPath = System.getProperty("user.dir");
        Path filePath = Path.of(fetchPath + Path.of("/resources/catFacts.txt"));

        createFileIfNotExist(filePath);

        try {
            while (retries != 0) {
                if (Files.size(filePath) == 0) {
                    Files.writeString(filePath, catFactService.getRandomCatFact() + "\n", StandardOpenOption.APPEND);
                } else if (!Files.readAllLines(filePath).contains(catFactService.getRandomCatFact())) {
                    Files.writeString(filePath, catFactService.getRandomCatFact() + "\n", StandardOpenOption.APPEND);
                    retries = 5;
                    Thread.sleep(1000);
                } else if (catFactService.getRandomCatFact() == null){
                    retries--;
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
    public static void createFileIfNotExist(Path filePath) {
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
