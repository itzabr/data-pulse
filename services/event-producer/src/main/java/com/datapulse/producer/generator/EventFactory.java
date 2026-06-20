package com.datapulse.producer.generator;

import com.datapulse.producer.model.event.BaseEvent;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class EventFactory {

    private final OrderEventGenerator orderGenerator;
    private final ProductViewGenerator productGenerator;
    private final CartAbandonedGenerator cartGenerator;
    private final ReviewGenerator reviewGenerator;

    private final Random random = new Random();

    public EventFactory(
            OrderEventGenerator orderGenerator,
            ProductViewGenerator productGenerator,
            CartAbandonedGenerator cartGenerator,
            ReviewGenerator reviewGenerator) {

        this.orderGenerator = orderGenerator;
        this.productGenerator = productGenerator;
        this.cartGenerator = cartGenerator;
        this.reviewGenerator = reviewGenerator;
    }

    public BaseEvent generateEvent() {

        int probability = random.nextInt(100);

        if (probability < 70) {
            return productGenerator.generate();
        }

        if (probability < 85) {
            return orderGenerator.generate();
        }

        if (probability < 95) {
            return cartGenerator.generate();
        }

        return reviewGenerator.generate();
    }
}
