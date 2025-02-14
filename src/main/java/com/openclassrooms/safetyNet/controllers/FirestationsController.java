package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.models.Firestations;
import com.openclassrooms.safetyNet.services.FirestationsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/firestation")
public class FirestationsController {

    private static final Logger logger = LogManager.getLogger(PersonsController.class);

    @Autowired
    private FirestationsService firestationsService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public Firestations addFireStation(@RequestBody Firestations firestations) {
        try {
            firestationsService.addFireStation(firestations);
        } catch (IOException e) {
            logger.error("Erreur lors de la création de la nouvelle station de pompier: {}", e.getMessage());
        }
        return firestations;
    }

    @PutMapping
    public Firestations modifyStation(@RequestBody Firestations firestationsToModify) {
        try {
            firestationsService.modifyFireStation(firestationsToModify);
        } catch (Exception e) {
            logger.error("Erreur lors de la modification de la station de pompier : {}", e.getMessage());
        }
        return firestationsToModify;
    }

    @DeleteMapping
    public boolean deleteStation(@RequestBody Firestations firestationsToDelete) {
        try {
            firestationsService.deleteStation(firestationsToDelete);
        } catch (Exception e) {
            logger.error("La suppression à échoué: {}", e.getMessage());
        }
        return true;
    }
}
