package com.datapulse.analytics.controller;

import com.datapulse.analytics.dto.SellerMetricsResponse;
import com.datapulse.analytics.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(
            AnalyticsService analyticsService) {

        this.analyticsService = analyticsService;
    }

    @GetMapping("/analytics/sellers")
    public List<SellerMetricsResponse> getAllSellers() {

        return analyticsService.getAllSellers();
    }

    @GetMapping("/analytics/seller/{sellerId}")
    public SellerMetricsResponse getSeller(
            @PathVariable String sellerId) {

        return analyticsService.getSeller(
                sellerId
        );
    }

    @GetMapping("/analytics/top-sellers")
    public List<SellerMetricsResponse> topSellers(
            @RequestParam int limit) {

        return analyticsService.topSellers(limit);
    }
}
