package com.openclassrooms.safetyNet.controllers;

import com.openclassrooms.safetyNet.models.Firestation;
import com.openclassrooms.safetyNet.result.StationCover;
import com.openclassrooms.safetyNet.services.IFireStationService;
import com.openclassrooms.safetyNet.services.impl.FirestationsService;
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

    private final IFireStationService firestationsService;

    public FirestationsController(IFireStationService firestationsService) {
        this.firestationsService = firestationsService;
    }

    /**
     *
     * @param stationNumber, numero de station de pompier
     * @return une liste de StationCover, nom des personnes couvertes par la caserne de pompiers avec l'adresse, numéro de téléphone,
     * ainsi que le decompte du nombre d'adultes et du nombre d'enfants
     * @throws IOException En cas d'erreur lors de l'accès aux données
     */
    @GetMapping()
    public List<StationCover> getPersonsCoverByFireStation(@RequestParam("stationNumber") int stationNumber) throws IOException {
        logger.info("Requête reçue pour obtenir les personnes couvertes par la station {}", stationNumber);
        try {
            return firestationsService.getCoverPersons(stationNumber);
        } catch (IOException e) {
            logger.error("Erreur lors de l'obtention des personnes couvertes par la station {} : {}", stationNumber, e.getMessage());
            throw e;
        }
    }

    /**
     *
     * @param firestation Objet Firestation à ajouter (contenant l'adresse et le numéro de station)
     */
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public void addFireStation(@RequestBody Firestation firestation) {
        logger.info("Requete addFireStation reçue, newFireStation {}", firestation);
        try {
            firestationsService.addFireStation(firestation);
            logger.info("Fin de la requete pour firestation: {}", firestation);
        } catch (IOException e) {
            logger.error("Erreur lors de la création de la nouvelle station de pompier: {}", e.getMessage());
        }
    }

    /**
     *
     * @param firestationToModify Objet Firestation contenant les données modifiées
     * @return L'objet Firestation modifié
     */
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PutMapping
    public Firestation modifyStation(@RequestBody Firestation firestationToModify) {
        logger.info("Requete modifyStation reçue: {}", firestationToModify);
        try {
            firestationsService.modifyFireStation(firestationToModify);
            logger.info("fireStation modifie: {}", firestationToModify);
        } catch (Exception e) {
            logger.error("Erreur lors de la modification de la station de pompier : {}", e.getMessage());
        }
        return firestationToModify;
    }

    /**
     *
     * @param firestationToDelete Objet Firestation à supprimer
     * @return true si la suppression a été effectuée (toujours true ici même si échec)
     */
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public boolean deleteStation(@RequestBody Firestation firestationToDelete) {
        logger.info("Requete deleteStation reçue: {}", firestationToDelete);
        try {
            firestationsService.deleteStation(firestationToDelete);
            logger.info("fireStation supprime: {}", firestationToDelete);
        } catch (Exception e) {
            logger.error("La suppression à échoué: {}", e.getMessage());
        }
        return true;
    }
}
