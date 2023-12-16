package org.geekhub.hw9.repository;

import java.util.concurrent.ConcurrentHashMap;

public interface Storage {
    void addWare(String product, int quantity);
    int getWareQuantity(String product);
    ConcurrentHashMap<String, Integer> getProductStorage();
}
