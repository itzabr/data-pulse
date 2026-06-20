package com.datapulse.producer.model.event;

import java.time.Instant;

public class ReviewSubmittedEvent extends BaseEvent {

    private String productId;
    private int rating;
    private String sellerId;

    public ReviewSubmittedEvent() {
        super();
    }

    public ReviewSubmittedEvent(
            String eventId,
            String customerId,
            Instant timestamp,
            String productId,
            int rating,
            String sellerId) {

        super(
                eventId,
                EventType.REVIEW_SUBMITTED,
                customerId,
                timestamp
        );

        this.productId = productId;
        this.rating = rating;
        this.sellerId = sellerId;
    }

    public String getProductId() {
        return productId;
    }

    public int getRating() {
        return rating;
    }

    public String getSellerId() {
        return sellerId;
    }
}
