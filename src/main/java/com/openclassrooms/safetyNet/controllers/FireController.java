package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.services.FireService;
import com.openclassrooms.safetyNet.result.FireHabitant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/fire")
public class FireController {

    private static final Logger logger = LogManager.getLogger(FireController.class);

    @Autowired
    private FireService fireService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FireHabitant getFireHabitantByAddress(@RequestParam("address") String address) throws IOException {
        logger.info("Requete recue");
        return fireService.getFireHabitantByAdress(address);
    }
}