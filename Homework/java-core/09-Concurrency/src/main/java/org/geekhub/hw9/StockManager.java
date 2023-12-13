package org.geekhub.hw9;

public class StockManager {

    private final OnlineStore onlineStore;
    private final int productDeliveryTime;
    private final int goodsQuantity;

    public StockManager(OnlineStore onlineStore, int productDeliveryTime, int goodsQuantity) {
        this.onlineStore = onlineStore;
        this.productDeliveryTime = productDeliveryTime;
        this.goodsQuantity = goodsQuantity;
    }
}
