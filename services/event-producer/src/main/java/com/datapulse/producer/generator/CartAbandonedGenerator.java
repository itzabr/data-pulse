package com.datapulse.producer.generator;

import com.datapulse.producer.model.event.CartAbandonedEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@Component
public class CartAbandonedGenerator {

    private final Random random = new Random();

    public CartAbandonedEvent generate() {

        return new CartAbandonedEvent(
                UUID.randomUUID().toString(),
                "CUSTOMER-" + random.nextInt(1000),
                Instant.now(),
                "CART-" + random.nextInt(10000),
                "PRODUCT-" + random.nextInt(500),
                "SELLER-" + random.nextInt(100)
        );
    }
}
