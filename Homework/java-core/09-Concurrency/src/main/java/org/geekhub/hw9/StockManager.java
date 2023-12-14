package org.geekhub.hw9;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class StockManager {

    private final OnlineStore onlineStore;
    private final long productDeliveryTime;
    private final int goodsQuantity;
    private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();


    public StockManager(OnlineStore onlineStore, long productDeliveryTime, int goodsQuantity) {
        this.onlineStore = onlineStore;
        this.productDeliveryTime = productDeliveryTime;
        this.goodsQuantity = goodsQuantity;
    }


    public void shutdown() {
        service.shutdown();
    }
}
