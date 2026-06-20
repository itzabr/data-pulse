package com.datapulse.producer.publisher;

import com.datapulse.producer.model.event.BaseEvent;

public interface EventPublisher {

    void publish(BaseEvent event);

}
