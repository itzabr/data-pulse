package com.datapulse.producer.generator;

import com.datapulse.producer.model.event.OrderLineItem;
import com.datapulse.producer.model.event.OrderPlacedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class OrderEventGenerator {

    private final Random random = new Random();

    public OrderPlacedEvent generate() {

        int customerNumber = random.nextInt(1000);
        int sellerNumber = random.nextInt(100);
        int orderNumber = random.nextInt(10000);

        OrderLineItem item =
                new OrderLineItem(
                        "PRODUCT-" + random.nextInt(500),
                        random.nextInt(5) + 1,
                        new BigDecimal(random.nextInt(5000) + 1000)
                );

        BigDecimal totalAmount =
                item.getUnitPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));

        return new OrderPlacedEvent(
                UUID.randomUUID().toString(),
                "CUSTOMER-" + customerNumber,
                Instant.now(),
                "ORDER-" + orderNumber,
                "SELLER-" + sellerNumber,
                List.of(item),
                totalAmount,
                "INR"
        );
    }
}
