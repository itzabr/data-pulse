package com.datapulse.producer.controller;

import com.datapulse.producer.generator.RandomEventGenerator;
import com.datapulse.producer.lambda.LambdaEventPublisher;
import com.datapulse.producer.model.event.BaseEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LambdaController {

    private final LambdaEventPublisher publisher;
    private final RandomEventGenerator randomEventGenerator;

    public LambdaController(
            LambdaEventPublisher publisher,
            RandomEventGenerator randomEventGenerator) {
        this.publisher = publisher;
        this.randomEventGenerator = randomEventGenerator;
    }

    @GetMapping("/lambda/test")
    public BaseEvent testLambda() throws Exception {
        BaseEvent event = randomEventGenerator.generate();
        publisher.publish(event);
        return event;
    }
}
