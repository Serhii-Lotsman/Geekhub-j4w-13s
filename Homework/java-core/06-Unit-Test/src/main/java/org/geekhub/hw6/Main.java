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

        String fetchPath = System.getProperty("user.dir");
        Path filePath = Path.of(fetchPath + Path.of("/resources/catFacts.txt"));
        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            Files.writeString(filePath, catFactService.getRandomCatFact() + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
