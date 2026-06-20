package com.datapulse.analytics.aggregation;

import com.datapulse.analytics.entity.SellerMetricsEntity;
import com.datapulse.analytics.model.event.OrderPlacedEvent;
import com.datapulse.analytics.repository.SellerMetricsJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class SellerMetricsAggregator {

    private final SellerMetricsJpaRepository repository;

    public SellerMetricsAggregator(
            SellerMetricsJpaRepository repository) {

        this.repository = repository;
    }

    @Transactional
    public void process(
            OrderPlacedEvent event) {

        SellerMetricsEntity metrics =
                repository.findById(event.getSellerId())
                        .orElse(new SellerMetricsEntity(event.getSellerId(), 0, BigDecimal.ZERO));

        metrics.addRevenue(
                event.getTotalAmount());
        
        repository.save(metrics);
    }
}
