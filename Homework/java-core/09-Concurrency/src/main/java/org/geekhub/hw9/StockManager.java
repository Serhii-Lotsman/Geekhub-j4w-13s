package org.geekhub.hw9;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StockManager {

    private final ScheduledExecutorService service;

    public StockManager(OnlineStore onlineStore, long productDeliveryTime, int goodsQuantity) {
        this.service = Executors.newSingleThreadScheduledExecutor();
        this.service.scheduleAtFixedRate(onlineStore.addGoods(goodsQuantity), 0, productDeliveryTime, TimeUnit.SECONDS);
    }

    public void shutdown() {
        service.shutdown();
    }
}
