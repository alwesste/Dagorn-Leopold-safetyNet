package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.result.FloodHabitant;
import com.openclassrooms.safetyNet.services.FloodService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/flood")
public class FloodControllers {

    private static final Logger logger = LogManager.getLogger(FloodControllers.class);

    @Autowired
    private FloodService floodService;

    @GetMapping("/stations")
    public FloodHabitant getFloodHabitant(@RequestParam ("stations") List<String> stations) throws IOException {
        return floodService.getHomeByStation(stations);
    }
}
