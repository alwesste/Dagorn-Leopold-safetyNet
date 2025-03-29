package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.models.Firestations;
import com.openclassrooms.safetyNet.result.StationCover;
import com.openclassrooms.safetyNet.services.FirestationsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/firestation")
public class FirestationsController {

    private static final Logger logger = LogManager.getLogger(FirestationsController.class);

    private final FirestationsService firestationsService;

    public FirestationsController(FirestationsService firestationsService) {
        this.firestationsService = firestationsService;
    }


    @GetMapping()
    public List<StationCover> getPersonsCoverByFireStation(@RequestParam("stationNumber") int stationNumber) throws IOException {
        logger.info("Requête reçue pour obtenir les personnes couvertes par la station {}", stationNumber);

        try {
            List<StationCover> result = firestationsService.getCoverPersons(stationNumber);
//            logger.info("Réponse envoyée pour la station {} : {} personnes couvertes", stationNumber, result.size());

//            if (logger.isDebugEnabled()) {
//                logger.debug();
//            }

            return result;
        } catch (IOException e) {
            logger.error("Erreur lors de l'obtention des personnes couvertes par la station {} : {}", stationNumber, e.getMessage());
            throw e;
        }
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public void addFireStation(@RequestBody Firestations firestations) {
        logger.info("Requete addFireStation reçue: {}", firestations);
        try {
            firestationsService.addFireStation(firestations);
            logger.info("fireStation créée: {}", firestations);
        } catch (IOException e) {
            logger.error("Erreur lors de la création de la nouvelle station de pompier: {}", e.getMessage());
        }
    }

    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PutMapping
    public Firestations modifyStation(@RequestBody Firestations firestationsToModify) {
        logger.info("Requete modifyStation reçue: {}", firestationsToModify);
        try {
            firestationsService.modifyFireStation(firestationsToModify);
            logger.info("fireStation modifie: {}", firestationsToModify);
        } catch (Exception e) {
            logger.error("Erreur lors de la modification de la station de pompier : {}", e.getMessage());
        }
        return firestationsToModify;
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public boolean deleteStation(@RequestBody Firestations firestationsToDelete) {
        logger.info("Requete deleteStation reçue: {}", firestationsToDelete);
        try {
            firestationsService.deleteStation(firestationsToDelete);
            logger.info("fireStation supprime: {}", firestationsToDelete);
        } catch (Exception e) {
            logger.error("La suppression à échoué: {}", e.getMessage());
        }
        return true;
    }
}
