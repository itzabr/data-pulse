package com.datapulse.analyticsservice.controller;

import com.datapulse.analyticsservice.dto.EventCountResponse;
import com.datapulse.analyticsservice.dto.RevenueLeaderboardResponse;
import com.datapulse.analyticsservice.dto.SellerMetricsResponse;
import com.datapulse.analyticsservice.dto.TopSellerResponse;
import com.datapulse.analyticsservice.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/health")
    public String health() {

        return "Analytics Service is running!";

    }

    @GetMapping("/analytics/event-counts")
    public List<EventCountResponse> getEventCounts() throws InterruptedException {

        return analyticsService.getEventCounts();

    }

    @GetMapping("/analytics/top-sellers")
    public List<TopSellerResponse> getTopSellers() {

        return analyticsService.getTopSellers();

    }

    @GetMapping("/analytics/seller/{sellerId}")
    public SellerMetricsResponse getSellerMetrics(

            @PathVariable String sellerId
    ) {

        return analyticsService.getSellerMetrics(
                sellerId
        );

    }

    @GetMapping("/analytics/revenue-leaderboard")
    public List<RevenueLeaderboardResponse> getRevenueLeaderboard() {

        return analyticsService.getRevenueLeaderboard();

    }

}
