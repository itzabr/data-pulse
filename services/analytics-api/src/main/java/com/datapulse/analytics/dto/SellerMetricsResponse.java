package com.datapulse.analytics.dto;

import java.math.BigDecimal;

public class SellerMetricsResponse {

    private String sellerId;
    private long totalOrders;
    private BigDecimal totalRevenue;
    private BigDecimal averageOrderValue;

    public SellerMetricsResponse(
            String sellerId,
            long totalOrders,
            BigDecimal totalRevenue,
            BigDecimal averageOrderValue) {

        this.sellerId = sellerId;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.averageOrderValue = averageOrderValue;
    }

    public String getSellerId() {
        return sellerId;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public BigDecimal getAverageOrderValue() {
        return averageOrderValue;
    }
}
