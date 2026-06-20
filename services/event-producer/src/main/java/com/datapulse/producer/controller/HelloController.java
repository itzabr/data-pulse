package com.datapulse.producer.controller;

import com.datapulse.producer.model.event.BaseEvent;
import com.datapulse.producer.service.SimulationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    private final SimulationService simulationService;

    public HelloController(
            SimulationService simulationService) {

        this.simulationService = simulationService;
    }

    @GetMapping("/simulation/start")
    public BaseEvent startSimulation() {

        return simulationService.generateEvent();

    }

    @GetMapping("/simulation/batch")
    public List<BaseEvent> generateBatch(
            @RequestParam int count) {

        return simulationService.generateEvents(count);

    }

    @GetMapping("/simulation/performance")
    public String performanceTest(
            @RequestParam int count) {

        long start = System.currentTimeMillis();

        simulationService.generateEvents(count);

        long end = System.currentTimeMillis();

        return "Time Taken: " + (end - start) + " ms";
    }

    @GetMapping("/simulation/performance-parallel")
    public String performanceParallel(
            @RequestParam int count) {

        long start = System.currentTimeMillis();

        simulationService.generateEventsParallel(count);

        long end = System.currentTimeMillis();

        return "Time Taken: " + (end - start) + " ms";
    }

    @GetMapping("/simulation/async")
    public String asyncSimulation(
            @RequestParam int count) {

        simulationService.generateEventsAsync(count);

        return "Simulation Started";
    }
}
