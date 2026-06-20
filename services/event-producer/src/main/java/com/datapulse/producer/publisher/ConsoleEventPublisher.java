package com.datapulse.producer.publisher;

import com.datapulse.producer.model.event.BaseEvent;
import org.springframework.stereotype.Component;

@Component
public class ConsoleEventPublisher implements EventPublisher {

    @Override
    public void publish(BaseEvent event) {

        System.out.println(
                "Publishing Event => " +
                        event.getEventType() +
                        " | " +
                        event.getEventId()
        );

    }
}
