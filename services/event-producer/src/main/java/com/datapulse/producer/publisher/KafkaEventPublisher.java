package com.datapulse.producer.publisher;

import com.datapulse.producer.kafka.KafkaTopics;
import com.datapulse.producer.model.event.BaseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Primary
public class KafkaEventPublisher
        implements EventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public KafkaEventPublisher(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper) {

        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(BaseEvent event) {

        try {

            String json =
                    objectMapper.writeValueAsString(event);

            kafkaTemplate.send(
                    KafkaTopics.EVENTS_TOPIC,
                    event.getCustomerId(),
                    json
            );

            System.out.println(
                    "Sent to Kafka : "
                            + event.getEventType()
            );

        }
        catch (Exception e) {

            throw new RuntimeException(
                    "Failed to publish event",
                    e
            );
        }
    }
}
