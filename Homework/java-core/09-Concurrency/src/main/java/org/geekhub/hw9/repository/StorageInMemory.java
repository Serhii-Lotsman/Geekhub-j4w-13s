package org.geekhub.hw9.repository;

import java.util.concurrent.ConcurrentHashMap;

public class StorageInMemory implements Storage {

    private final ConcurrentHashMap<String, Integer> productStorage = new ConcurrentHashMap<>();

    @Override
    public void addGoods(String product, int quantity) {
        productStorage.put(product, quantity);
    }

    @Override
    public int getGoodsQuantity(String product) {
        return productStorage.getOrDefault(product, 0);
    }

    @Override
    public ConcurrentHashMap<String, Integer> getGoodsStorage() {
        return productStorage;
    }
}
