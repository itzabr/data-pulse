package com.datapulse.analyticsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopSellerResponse {

    private String sellerId;

    private Long revenue;

}
