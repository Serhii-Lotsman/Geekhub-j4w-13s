package org.geekhub.hw9.repository;

import java.util.concurrent.ConcurrentHashMap;

public interface Storage {
    void addGoods(String product, int quantity);
    int getGoodsQuantity(String product);
    ConcurrentHashMap<String, Integer> getGoodsStorage();
}
