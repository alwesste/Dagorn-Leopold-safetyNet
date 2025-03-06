package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.models.Firestations;
import com.openclassrooms.safetyNet.result.StationCover;
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

    @GetMapping("/stationNumber")
    public StationCover getPersonsCoverByFireStation(@RequestParam("stationNumber") int stationNumber) throws IOException {
        logger.info("Requête reçue pour obtenir les personnes couvertes par la station {}", stationNumber);
        logger.info("Requete recue");
        return firestationsService.getCoverPersons(stationNumber);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public void addFireStation(@RequestBody Firestations firestations) {
        try {
            logger.info("Requete addFireStation reçue");
            firestationsService.addFireStation(firestations);
            logger.info("fireStation créée");
        } catch (IOException e) {
            logger.error("Erreur lors de la création de la nouvelle station de pompier: {}", e.getMessage());
        }
    }

    @PutMapping
    public Firestations modifyStation(@RequestBody Firestations firestationsToModify) {
        try {
            logger.info("Requete modifyStation reçue");
            firestationsService.modifyFireStation(firestationsToModify);
            logger.info("fireStation modifie");
        } catch (Exception e) {
            logger.error("Erreur lors de la modification de la station de pompier : {}", e.getMessage());
        }
        return firestationsToModify;
    }

    @DeleteMapping
    public boolean deleteStation(@RequestBody Firestations firestationsToDelete) {
        try {
            logger.info("Requete deleteStation reçue");
            firestationsService.deleteStation(firestationsToDelete);
            logger.info("fireStation supprime");
        } catch (Exception e) {
            logger.error("La suppression à échoué: {}", e.getMessage());
        }
        return true;
    }
}
