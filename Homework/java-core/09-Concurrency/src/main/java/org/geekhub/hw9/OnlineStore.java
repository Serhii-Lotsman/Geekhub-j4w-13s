package org.geekhub.hw9;

import org.geekhub.hw9.repository.Storage;
import org.geekhub.hw9.repository.StorageInMemory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class OnlineStore {

    private final Storage storage;
    private final AtomicInteger totalSales;
    private final ExecutorService executor;

    public OnlineStore(ExecutorService executor) {
        this.storage = new StorageInMemory();
        this.totalSales = new AtomicInteger(0);
        this.executor = executor;
    }

    public void addProduct(String product, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity should be non-negative");
        } else storage.addGoods(product, getProductQuantity(product) + quantity);
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
