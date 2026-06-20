package com.datapulse.analyticsservice.controller;

import com.datapulse.analyticsservice.athena.AthenaQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AthenaController {

    private final AthenaQueryService athenaQueryService;

    @GetMapping("/athena/test")
    public String testAthena() {

        return athenaQueryService.executeQuery();

    }

}
