package com.datapulse.producer.publisher;

import com.datapulse.producer.exception.EventSerializationException;
import com.datapulse.producer.model.event.BaseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class JsonEventPublisher implements EventPublisher {

    private final ObjectMapper objectMapper;

    public JsonEventPublisher(
            ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(BaseEvent event) {

        try {

            String json =
                    objectMapper.writeValueAsString(event);

            System.out.println(
                    "Publishing JSON Event:\n" +
                            json
            );

        }
        catch (JsonProcessingException e) {

            throw new EventSerializationException(
                    "Failed to serialize event",
                    e
            );

        }

    }
}
