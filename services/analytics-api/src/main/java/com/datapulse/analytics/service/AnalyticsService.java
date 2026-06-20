package com.datapulse.analytics.service;

import com.datapulse.analytics.dto.SellerMetricsResponse;
import com.datapulse.analytics.entity.SellerMetricsEntity;
import com.datapulse.analytics.mapper.SellerMetricsMapper;
import com.datapulse.analytics.repository.SellerMetricsJpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final SellerMetricsJpaRepository repository;
    private final SellerMetricsMapper mapper;

    public AnalyticsService(
            SellerMetricsJpaRepository repository,
            SellerMetricsMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    public List<SellerMetricsResponse> getAllSellers() {
        return repository.findAll()
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public SellerMetricsResponse getSeller(String sellerId) {
        SellerMetricsEntity entity = repository.findById(sellerId)
                .orElse(new SellerMetricsEntity(sellerId, 0, BigDecimal.ZERO));
        return mapper.map(entity);
    }

    public List<SellerMetricsResponse> topSellers(int limit) {
        return repository.findAll()
                .stream()
                .sorted((a, b) -> b.getTotalRevenue().compareTo(a.getTotalRevenue()))
                .limit(limit)
                .map(mapper::map)
                .collect(Collectors.toList());
    }
}
