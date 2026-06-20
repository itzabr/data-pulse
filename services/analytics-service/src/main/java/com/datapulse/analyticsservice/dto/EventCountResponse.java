package com.datapulse.analyticsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventCountResponse {

    private String eventType;

    private Long count;

}
