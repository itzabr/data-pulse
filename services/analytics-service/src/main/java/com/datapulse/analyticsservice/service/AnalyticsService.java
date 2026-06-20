package com.datapulse.analyticsservice.service;

import com.datapulse.analyticsservice.dto.EventCountResponse;
import com.datapulse.analyticsservice.dto.RevenueLeaderboardResponse;
import com.datapulse.analyticsservice.dto.SellerMetricsResponse;
import com.datapulse.analyticsservice.dto.TopSellerResponse;
import com.datapulse.analyticsservice.repository.SellerMetricsRepository;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import java.util.Map;
import org.springframework.stereotype.Service;

import java.util.List;
import com.datapulse.analyticsservice.athena.AthenaQueryService;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final SellerMetricsRepository repository;
    private final AthenaQueryService athenaQueryService;

    public List<EventCountResponse> getEventCounts() throws InterruptedException {

        return athenaQueryService.getEventCounts();

    }

    public List<TopSellerResponse> getTopSellers() {

        return List.of(

                new TopSellerResponse(
                        "SELLER-8",
                        11728L
                ),

                new TopSellerResponse(
                        "SELLER-35",
                        4173L
                ),

                new TopSellerResponse(
                        "SELLER-14",
                        3596L
                )

        );

    }

    private long parseLongSafe(Map<String, AttributeValue> item, String key) {
        if (item != null && item.containsKey(key) && item.get(key).n() != null) {
            return Long.parseLong(item.get(key).n());
        }
        return 0L;
    }

    public SellerMetricsResponse getSellerMetrics(
            String sellerId
    ) {

        var item =
                repository.getSellerMetrics(
                        sellerId
                );

        if (item == null || item.isEmpty()) {
            return new SellerMetricsResponse(sellerId, 0L, 0L, 0L, 0L, 0L);
        }

        return new SellerMetricsResponse(
                sellerId,
                parseLongSafe(item, "totalOrders"),
                parseLongSafe(item, "totalRevenue"),
                parseLongSafe(item, "productViews"),
                parseLongSafe(item, "abandonedCarts"),
                parseLongSafe(item, "reviewCount")
        );

    }

    public List<RevenueLeaderboardResponse> getRevenueLeaderboard() {

        return List.of(

                new RevenueLeaderboardResponse(
                        "SELLER-001",
                        275000L
                ),

                new RevenueLeaderboardResponse(
                        "SELLER-35",
                        4173L
                ),

                new RevenueLeaderboardResponse(
                        "SELLER-14",
                        3596L
                )

        );

    }

}
