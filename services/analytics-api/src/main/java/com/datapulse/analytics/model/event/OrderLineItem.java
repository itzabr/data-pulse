package com.datapulse.analytics.model.event;

import java.math.BigDecimal;

public class OrderLineItem {

    private final String productId;
    private final int quantity;
    private final BigDecimal unitPrice;

    public OrderLineItem(
            String productId,
            int quantity,
            BigDecimal unitPrice) {

        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
