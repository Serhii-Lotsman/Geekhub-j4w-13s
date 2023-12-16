package org.geekhub.hw9;

import org.geekhub.hw9.repository.Storage;
import org.geekhub.hw9.repository.StorageInMemory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OnlineStore {

    private static final Storage storage = new StorageInMemory();
    private volatile int totalSales = 0;

    public void addProduct(String product, int quantity) {
        storage.addWare(product, quantity);
    }

    public Future<Boolean> purchase(String product, int quantity) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            return executor.submit(() -> {
                if (storage.getProductStorage().containsKey(product) && storage.getProductStorage().get(product) >= quantity) {
                    storage.getProductStorage().compute(product, (ware, amount) -> amount == null ? 0 : amount - quantity);
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
        return storage.getWareQuantity(product);
    }

    public int getTotalSales() {
        return totalSales;
    }

    public synchronized Runnable addGoods(int goodsQuantity) {
        return () -> storage.getProductStorage().forEach((key, value) -> {
            int updatedValue = value + goodsQuantity;
            storage.getProductStorage().put(key, updatedValue);
        });
    }
}
