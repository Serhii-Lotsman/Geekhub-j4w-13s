package org.geekhub.hw9;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OnlineStore implements Runnable{

    @Override
    public void run() {
        System.out.println("Run");
    }

    private final ConcurrentHashMap<String, Integer> productStorage = new ConcurrentHashMap<>();
    private volatile int totalSales = 0;

    public void addProduct(String product, int quantity) {
        productStorage.put(product, quantity);
    }

    public Future<Boolean> purchase(String product, int quantity) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            return executor.submit(() -> {
                if (productStorage.containsKey(product) && productStorage.get(product) >= quantity) {
                    productStorage.compute(product, (ware, amount) -> amount - quantity);
                    synchronized (this) {
                        this.totalSales += quantity;
                    }
                    return true;
                } else {
                    return false;
                }
            });
        } finally {
            executor.shutdown();
        }
    }

    public int getProductQuantity(String product) {
        return productStorage.getOrDefault(product, 0);
    }

    public int getTotalSales() {
        return totalSales;
    }
}
