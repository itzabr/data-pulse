package com.datapulse.producer.lambda;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.datapulse.producer.model.event.BaseEvent;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;

@Service
public class LambdaEventPublisher {

    private final LambdaClient lambdaClient;
    private final ObjectMapper objectMapper;

    public LambdaEventPublisher(
            LambdaClient lambdaClient,
            ObjectMapper objectMapper) {

        this.lambdaClient = lambdaClient;
        this.objectMapper = objectMapper;
    }

    public void publish(BaseEvent event) throws Exception {
        String payload = objectMapper.writeValueAsString(event);

        InvokeRequest request = InvokeRequest.builder()
                .functionName("datapulse-hello")
                .payload(SdkBytes.fromUtf8String(payload))
                .build();

        var response = lambdaClient.invoke(request);

        System.out.println("Published event -> " + event.getEventType());
    }
}
