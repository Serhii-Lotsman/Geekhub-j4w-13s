package org.geekhub.hw9;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OnlineStore {

    private final ConcurrentHashMap<String, Integer> productStorage = new ConcurrentHashMap<>();
    private int totalSales = 0;

    public void addProduct(String product, int quantity) {
        productStorage.put(product, quantity);
    }

    public Future<Boolean> purchase(String product, int quantity) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executor.submit(() -> {
            if (productStorage.containsKey(product) && productStorage.get(product) >= quantity) {
                int currentQuantity = productStorage.get(product);
                productStorage.put(product, currentQuantity - quantity);
                totalSales += quantity;
                return true;
            } else {
                return false;
            }
        });

        executor.shutdown();

        return future;
    }

    public int getProductQuantity(String product) {
        return productStorage.getOrDefault(product, 0);
    }

    public int getTotalSales() {
        return totalSales;
    }
}
