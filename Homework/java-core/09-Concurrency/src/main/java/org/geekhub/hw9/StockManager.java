package org.geekhub.hw9;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StockManager {

    private final ScheduledExecutorService service;

    public StockManager(OnlineStore onlineStore, long period, int addQuantity) {
        this.service = Executors.newSingleThreadScheduledExecutor();
        this.service.scheduleAtFixedRate(onlineStore.addGoods(addQuantity), 1, period, TimeUnit.SECONDS);
    }

    public void shutdown() {
        service.shutdown();
    }
}
