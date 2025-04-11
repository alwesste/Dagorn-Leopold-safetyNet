package com.openclassrooms.safetyNet.repository;

import com.openclassrooms.safetyNet.exceptions.FireStationAlreadyExistsException;
import com.openclassrooms.safetyNet.exceptions.FirestationNotFoundException;
import com.openclassrooms.safetyNet.services.IJsonFileHandler;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class FirestationRepository {

    private final Logger logger = LogManager.getLogger(FirestationRepository.class);

    @Autowired
    IJsonFileHandler jsonFileHandler;

    public void addFireStation(Firestation newFirestation) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<Firestation> fireStationList = jsonFile.getFirestations();
        logger.debug("La nouvelle FireStationa a ajouter: {}", newFirestation);

        boolean firestationAlreadyExist = fireStationList.stream()
                .anyMatch(firestations -> firestations.getAddress().equalsIgnoreCase(newFirestation.getAddress()) &&
                        firestations.getStation().equalsIgnoreCase(newFirestation.getStation()));

        if (firestationAlreadyExist) {
            logger.warn("La fireStation existe déjà dans data.json: {} {}", newFirestation.getAddress(), newFirestation.getStation());
            throw new FireStationAlreadyExistsException("Erreur lors de l'ajout. FireStation déjà enregistrée avec cette adresse et station.");
        }

        fireStationList.add(newFirestation);
        jsonFileHandler.writeJsonFile(jsonFile);
    }

    public void modifyFireStation(Firestation firestationModified) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<Firestation> fireStationList = jsonFile.getFirestations();

        Optional<Firestation> oFirestationsToModify = fireStationList.stream()
                .filter(firestations -> firestations.getAddress().equalsIgnoreCase(firestationModified.getAddress()))
                .findFirst();
        logger.debug("Station a modifie {}", oFirestationsToModify);


        if (oFirestationsToModify.isPresent()) {
            Firestation firestation = oFirestationsToModify.get(); //orElseGet()
            firestation.setStation(firestationModified.getStation());
        }

        jsonFileHandler.writeJsonFile(jsonFile);

    }

    public void deleteFireStation(Firestation firestationToDelete) throws IOException, FirestationNotFoundException {

        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<Firestation> firestationList = jsonFile.getFirestations();

        boolean isRemoved = firestationList.removeIf(firestations ->
                firestations.getAddress().equalsIgnoreCase(firestationToDelete.getAddress()) &&
                        firestations.getStation().equalsIgnoreCase(firestationToDelete.getStation())
        );

        if (!isRemoved) {
            throw new FirestationNotFoundException("FireStation introuvable avec le prénom et le nom fournis.");
        }
        logger.debug("FireStation a Supprimer: {}", firestationToDelete);

        jsonFileHandler.writeJsonFile(jsonFile);
    }
}
