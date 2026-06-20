package com.datapulse.analytics.model.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class OrderPlacedEvent extends BaseEvent {

    private String orderId;
    private String sellerId;
    private List<OrderLineItem> items;
    private BigDecimal totalAmount;
    private String currency;

    public OrderPlacedEvent() {
        super();
    }

    public OrderPlacedEvent(
            String eventId,
            String customerId,
            Instant timestamp,
            String orderId,
            String sellerId,
            List<OrderLineItem> items,
            BigDecimal totalAmount,
            String currency) {

        super(
                eventId,
                EventType.ORDER_PLACED,
                customerId,
                timestamp
        );

        this.orderId = orderId;
        this.sellerId = sellerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.currency = currency;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public List<OrderLineItem> getItems() {
        return items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getCurrency() {
        return currency;
    }
}
