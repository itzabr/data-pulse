package com.datapulse.producer.generator;

import com.datapulse.producer.model.event.ProductViewedEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@Component
public class ProductViewGenerator {

    private final Random random = new Random();

    public ProductViewedEvent generate() {

        return new ProductViewedEvent(
                UUID.randomUUID().toString(),
                "CUSTOMER-" + random.nextInt(1000),
                Instant.now(),
                "PRODUCT-" + random.nextInt(500),
                "SELLER-" + random.nextInt(100)
        );
    }
}
