package com.datapulse.producer.generator;

import com.datapulse.producer.model.event.*;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomEventGenerator {

    private final OrderEventGenerator orderGenerator;
    private final ProductViewGenerator productViewGenerator;
    private final CartAbandonedGenerator cartAbandonedGenerator;
    private final ReviewGenerator reviewGenerator;

    public RandomEventGenerator(
            OrderEventGenerator orderGenerator,
            ProductViewGenerator productViewGenerator,
            CartAbandonedGenerator cartAbandonedGenerator,
            ReviewGenerator reviewGenerator) {
        this.orderGenerator = orderGenerator;
        this.productViewGenerator = productViewGenerator;
        this.cartAbandonedGenerator = cartAbandonedGenerator;
        this.reviewGenerator = reviewGenerator;
    }

    public BaseEvent generate() {
        int value = new Random().nextInt(4);

        switch (value) {
            case 0:
                return orderGenerator.generate();
            case 1:
                return productViewGenerator.generate();
            case 2:
                return cartAbandonedGenerator.generate();
            default:
                return reviewGenerator.generate();
        }
    }
}
