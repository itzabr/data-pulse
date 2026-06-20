package com.datapulse.analytics.consumer;

import com.datapulse.analytics.kafka.KafkaTopics;
import com.datapulse.analytics.model.event.BaseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.datapulse.analytics.service.EventProcessorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final ObjectMapper objectMapper;
    private final EventProcessorService processorService;
    
    @Value("${server.port}")
    private String port;

    public KafkaConsumerService(ObjectMapper objectMapper, EventProcessorService processorService) {
        this.objectMapper = objectMapper;
        this.processorService = processorService;
    }

    @KafkaListener(
            topics = KafkaTopics.EVENTS_TOPIC
    )
    public void consume(String message, Acknowledgment acknowledgment) {
        try {
            BaseEvent event =
                    objectMapper.readValue(
                            message,
                            BaseEvent.class
                    );

            System.out.println(
                    "Consumer on port "
                            + port
                            + " processed "
                            + event.getEventType()
            );

            processorService.process(event);
            
            acknowledgment.acknowledge();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
