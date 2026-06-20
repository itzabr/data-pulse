package com.datapulse.analytics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class SellerMetricsEntity {

    @Id
    private String sellerId;

    private long totalOrders;

    private BigDecimal totalRevenue;

    public SellerMetricsEntity() {
    }

    public SellerMetricsEntity(
            String sellerId,
            long totalOrders,
            BigDecimal totalRevenue) {

        this.sellerId = sellerId;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(
            BigDecimal totalRevenue) {

        this.totalRevenue = totalRevenue;
    }

    public void addRevenue(BigDecimal amount) {
        this.totalOrders++;
        this.totalRevenue = this.totalRevenue.add(amount);
    }

    public BigDecimal getAverageOrderValue() {
        if (totalOrders == 0) {
            return BigDecimal.ZERO;
        }
        return totalRevenue.divide(
                BigDecimal.valueOf(totalOrders), 2, java.math.RoundingMode.HALF_UP
        );
    }
}
