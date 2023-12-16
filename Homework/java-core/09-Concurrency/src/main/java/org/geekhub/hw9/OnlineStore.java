package org.geekhub.hw9;

import org.geekhub.hw9.repository.Storage;
import org.geekhub.hw9.repository.StorageInMemory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OnlineStore {

    private final Storage storage = new StorageInMemory();
    private volatile int totalSales = 0;

    public void addProduct(String product, int quantity) {
        storage.addGoods(product, quantity);
    }

    public Future<Boolean> purchase(String product, int quantity) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            return executor.submit(() -> {
                if (storage.getGoodsStorage().containsKey(product) &&
                    storage.getGoodsStorage().get(product) >= quantity) {
                    storage.getGoodsStorage().compute(product, (goods, amount) ->
                        amount == null ? 0 : amount - quantity);
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
        return storage.getGoodsQuantity(product);
    }

    public int getTotalSales() {
        return totalSales;
    }

    public Runnable addGoods(int addQuantity) {
        return () -> storage.getGoodsStorage().forEach((key, value) -> {
            int updatedValue = value + addQuantity;
            storage.getGoodsStorage().put(key, updatedValue);
        });
    }
}
