package com.datapulse.analytics.model.event;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "eventType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = OrderPlacedEvent.class,
                name = "ORDER_PLACED"
        ),
        @JsonSubTypes.Type(
                value = ProductViewedEvent.class,
                name = "PRODUCT_VIEWED"
        ),
        @JsonSubTypes.Type(
                value = CartAbandonedEvent.class,
                name = "CART_ABANDONED"
        ),
        @JsonSubTypes.Type(
                value = ReviewSubmittedEvent.class,
                name = "REVIEW_SUBMITTED"
        )
})
public abstract class BaseEvent {

    private String eventId;
    private EventType eventType;
    private String customerId;
    private Instant timestamp;

    protected BaseEvent() {
    }

    public BaseEvent(
            String eventId,
            EventType eventType,
            String customerId,
            Instant timestamp) {

        this.eventId = eventId;
        this.eventType = eventType;
        this.customerId = customerId;
        this.timestamp = timestamp;
    }

    public String getEventId() {
        return eventId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

}

