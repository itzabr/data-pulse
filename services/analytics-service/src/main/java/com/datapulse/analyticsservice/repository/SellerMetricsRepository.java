package com.datapulse.analyticsservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SellerMetricsRepository {

    private final DynamoDbClient dynamoDbClient;

    public Map<String, software.amazon.awssdk.services.dynamodb.model.AttributeValue>
    getSellerMetrics(String sellerId) {

        GetItemRequest request =
                GetItemRequest.builder()
                        .tableName("seller-metrics")
                        .key(
                                Map.of(
                                        "sellerId",
                                        software.amazon.awssdk.services.dynamodb.model.AttributeValue
                                                .builder()
                                                .s(sellerId)
                                                .build()
                                )
                        )
                        .build();

        return dynamoDbClient
                .getItem(request)
                .item();

    }

}
