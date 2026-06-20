package com.datapulse.analyticsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SellerMetricsResponse {

    private String sellerId;

    private Long totalOrders;

    private Long totalRevenue;

    private Long productViews;

    private Long abandonedCarts;

    private Long reviewCount;

}
