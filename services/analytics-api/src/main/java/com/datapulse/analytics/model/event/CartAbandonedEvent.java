package com.datapulse.analytics.model.event;

import java.time.Instant;

public class CartAbandonedEvent extends BaseEvent {

    private String cartId;
    private String productId;
    private String sellerId;

    public CartAbandonedEvent() {
        super();
    }

    public CartAbandonedEvent(
            String eventId,
            String customerId,
            Instant timestamp,
            String cartId,
            String productId,
            String sellerId) {

        super(
                eventId,
                EventType.CART_ABANDONED,
                customerId,
                timestamp
        );

        this.cartId = cartId;
        this.productId = productId;
        this.sellerId = sellerId;
    }

    public String getCartId() {
        return cartId;
    }

    public String getProductId() {
        return productId;
    }

    public String getSellerId() {
        return sellerId;
    }
}
