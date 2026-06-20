package com.datapulse.analyticsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RevenueLeaderboardResponse {

    private String sellerId;

    private Long revenue;

}
