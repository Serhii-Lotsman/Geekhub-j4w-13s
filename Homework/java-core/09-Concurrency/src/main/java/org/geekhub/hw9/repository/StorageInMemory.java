package org.geekhub.hw9.repository;

import java.util.concurrent.ConcurrentHashMap;

public class StorageInMemory implements Storage {

    private final ConcurrentHashMap<String, Integer> productStorage = new ConcurrentHashMap<>();

    @Override
    public void addWare(String product, Integer quantity) {
        productStorage.put(product, quantity);
    }

    @Override
    public int getWareQuantity(String product) {
        return productStorage.getOrDefault(product, 0);
    }

    @Override
    public ConcurrentHashMap<String, Integer> getProductStorage() {
        return productStorage;
    }
}
