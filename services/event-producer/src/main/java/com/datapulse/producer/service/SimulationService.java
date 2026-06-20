package com.datapulse.producer.service;

import com.datapulse.producer.generator.EventFactory;
import com.datapulse.producer.model.event.BaseEvent;
import com.datapulse.producer.publisher.EventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SimulationService {

    private final EventFactory eventFactory;
    private final ExecutorService executorService;
    private final EventPublisher eventPublisher;

    public SimulationService(
            EventFactory eventFactory,
            ExecutorService executorService,
            EventPublisher eventPublisher) {

        this.eventFactory = eventFactory;
        this.executorService = executorService;
        this.eventPublisher = eventPublisher;
    }

    public BaseEvent generateEvent() {

        BaseEvent event =
                eventFactory.generateEvent();

        eventPublisher.publish(event);

        return event;
    }

    public List<BaseEvent> generateEvents(int count) {

        return IntStream.range(0, count)
                .mapToObj(i -> generateEvent())
                .collect(Collectors.toList());

    }

    public List<BaseEvent> generateEventsParallel(int count) {

        return IntStream.range(0, count)
                .parallel()
                .mapToObj(i -> generateEvent())
                .collect(Collectors.toList());

    }

    public CompletableFuture<List<BaseEvent>> generateEventsAsync(int count) {

        return CompletableFuture.supplyAsync(
                () -> generateEventsParallel(count),
                executorService
        );
    }
}
