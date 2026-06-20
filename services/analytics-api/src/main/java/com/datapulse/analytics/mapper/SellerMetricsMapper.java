package com.datapulse.analytics.mapper;

import com.datapulse.analytics.dto.SellerMetricsResponse;
import com.datapulse.analytics.entity.SellerMetricsEntity;
import org.springframework.stereotype.Component;

@Component
public class SellerMetricsMapper {

    public SellerMetricsResponse map(
            SellerMetricsEntity metrics) {

        return new SellerMetricsResponse(
                metrics.getSellerId(),
                metrics.getTotalOrders(),
                metrics.getTotalRevenue(),
                metrics.getAverageOrderValue()
        );
    }
}
