package com.datapulse.analytics.repository;

import com.datapulse.analytics.entity.SellerMetricsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerMetricsJpaRepository
        extends JpaRepository<
        SellerMetricsEntity,
        String> {

}
