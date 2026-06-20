package com.datapulse.producer.generator;

import com.datapulse.producer.model.event.ReviewSubmittedEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@Component
public class ReviewGenerator {

    private final Random random = new Random();

    public ReviewSubmittedEvent generate() {

        return new ReviewSubmittedEvent(
                UUID.randomUUID().toString(),
                "CUSTOMER-" + random.nextInt(1000),
                Instant.now(),
                "PRODUCT-" + random.nextInt(500),
                random.nextInt(5) + 1,
                "SELLER-" + random.nextInt(100)
        );
    }
}
