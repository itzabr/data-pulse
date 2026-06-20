package com.datapulse.analyticsservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.athena.AthenaClient;

@Configuration
public class AthenaConfig {

    @Bean
    public AthenaClient athenaClient() {

        return AthenaClient.builder()
                .region(Region.AP_SOUTH_1)
                .build();

    }

}
