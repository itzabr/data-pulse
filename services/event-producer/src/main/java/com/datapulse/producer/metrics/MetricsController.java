package com.datapulse.producer.metrics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(
            MetricsService metricsService) {

        this.metricsService =
                metricsService;
    }

    @GetMapping("/metrics")
    public String metrics() {

        return metricsService.metrics();

    }
}
