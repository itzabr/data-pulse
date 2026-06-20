package com.datapulse.analytics.model.event;

import java.time.Instant;

public class ProductViewedEvent extends BaseEvent {

    private String productId;
    private String sellerId;

    public ProductViewedEvent() {
        super();
    }

    public ProductViewedEvent(
            String eventId,
            String customerId,
            Instant timestamp,
            String productId,
            String sellerId) {

        super(
                eventId,
                EventType.PRODUCT_VIEWED,
                customerId,
                timestamp
        );

        this.productId = productId;
        this.sellerId = sellerId;
    }

    public String getProductId() {
        return productId;
    }

    public String getSellerId() {
        return sellerId;
    }
}
