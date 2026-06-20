package com.datapulse.analytics.service;

import com.datapulse.analytics.aggregation.SellerMetricsAggregator;
import com.datapulse.analytics.model.event.BaseEvent;
import com.datapulse.analytics.model.event.OrderPlacedEvent;
import com.datapulse.analytics.model.event.ProductViewedEvent;
import com.datapulse.analytics.model.event.ReviewSubmittedEvent;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventProcessorService {

    private final SellerMetricsAggregator aggregator;
    private final IdempotencyService idempotencyService;
    private final Counter eventCounter;
    private final Counter orderCounter;
    private final Counter productCounter;
    private final Counter reviewCounter;

    public EventProcessorService(
            SellerMetricsAggregator aggregator,
            IdempotencyService idempotencyService,
            MeterRegistry registry) {

        this.aggregator = aggregator;
        this.idempotencyService = idempotencyService;
        
        this.eventCounter = registry.counter("events_processed_total");
        this.orderCounter = registry.counter("orders_processed_total");
        this.productCounter = registry.counter("products_viewed_total");
        this.reviewCounter = registry.counter("reviews_submitted_total");
    }

    @Transactional
    public void process(BaseEvent event) {

        if(idempotencyService.alreadyProcessed(
                event.getEventId()))
        {
            return;
        }

        eventCounter.increment();

        if (event instanceof OrderPlacedEvent) {
            orderCounter.increment();
            OrderPlacedEvent order = (OrderPlacedEvent) event;
            aggregator.process(order);
        } else if (event instanceof ProductViewedEvent) {
            productCounter.increment();
        } else if (event instanceof ReviewSubmittedEvent) {
            reviewCounter.increment();
        }

        idempotencyService.markProcessed(
                event.getEventId()
        );
    }
}
