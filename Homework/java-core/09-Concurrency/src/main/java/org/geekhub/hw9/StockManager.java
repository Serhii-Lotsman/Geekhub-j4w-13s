package org.geekhub.hw9;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StockManager implements Runnable{

    private final OnlineStore onlineStore;
    private final long productDeliveryTime;
    private final int goodsQuantity;
    private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void run() {
        service.scheduleAtFixedRate(onlineStore.addGoods("product", goodsQuantity), 0, productDeliveryTime, TimeUnit.SECONDS);
    }

    public StockManager(OnlineStore onlineStore, long productDeliveryTime, int goodsQuantity) {
        this.onlineStore = onlineStore;
        this.productDeliveryTime = productDeliveryTime;
        this.goodsQuantity = goodsQuantity;
    }

    public void shutdown() {
        service.shutdown();
    }
}
