package com.datapulse.producer.publisher;

import com.datapulse.producer.model.event.BaseEvent;
import com.datapulse.producer.partition.EventPartitionManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class StreamEventPublisher
        implements EventPublisher {

    private final EventPartitionManager eventPartitionManager;

    public StreamEventPublisher(
            EventPartitionManager eventPartitionManager) {

        this.eventPartitionManager = eventPartitionManager;
    }

    @Override
    public void publish(BaseEvent event) {

        try {

            eventPartitionManager.publish(event);

        }
        catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }
    }
}
