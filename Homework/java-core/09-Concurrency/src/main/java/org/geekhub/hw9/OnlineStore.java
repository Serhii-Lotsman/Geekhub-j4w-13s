package org.geekhub.hw9;

import org.geekhub.hw9.repository.Storage;
import org.geekhub.hw9.repository.StorageInMemory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class OnlineStore {

    private final Storage storage;
    private final AtomicInteger totalSales;
    private final ExecutorService executor;

    public OnlineStore() {
        this.storage = new StorageInMemory();
        this.totalSales = new AtomicInteger(0);
        this.executor = Executors.newFixedThreadPool(10);
    }

    public void addProduct(String product, int quantity) {
        storage.addGoods(product, getProductQuantity(product) + quantity);
    }

    public Future<Boolean> purchase(String product, int quantity) {
        return executor.submit( () -> {
            if (storage.getGoodsStorage().containsKey(product) &&
                storage.getGoodsStorage().get(product) >= quantity) {
                storage.getGoodsStorage().compute(product, (goods, amount) ->
                    amount == null ? 0 : amount - quantity);
                totalSales.addAndGet(quantity);
                return true;
            }
            return false;
        });
    }

    public int getProductQuantity(String product) {
        return storage.getGoodsQuantity(product);
    }

    public int getTotalSales() {
        return totalSales.get();
    }

    public Runnable addGoods(int addQuantity) {
        return () -> storage.getGoodsStorage().forEach((key, value) -> {
            int updatedValue = value + addQuantity;
            storage.getGoodsStorage().put(key, updatedValue);
        });
    }
}
