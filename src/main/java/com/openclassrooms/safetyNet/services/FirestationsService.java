package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.exceptions.FiretationNotFoundException;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FirestationsService {

    private static final Logger logger = LogManager.getLogger(FirestationsService.class);

    public void addFireStation(Firestations newFirestations) throws IOException {
        DataJsonHandler jsonFile = JsonFileHandler.readJsonFile();
        List<Firestations> fireStationList = jsonFile.getFirestations();

        fireStationList.add(newFirestations);
        JsonFileHandler.writeJsonFile(jsonFile);
    }


    public void modifyFireStation(Firestations firestationModified) throws IOException {
        DataJsonHandler jsonFile = JsonFileHandler.readJsonFile();
        List<Firestations> fireStationList = jsonFile.getFirestations();

        try {
            Optional<Firestations> firestationsToModify = fireStationList.stream()
                    .filter(firestations -> firestations.getAddress().equalsIgnoreCase(firestationModified.getAddress()))
                    .findFirst();

            if (firestationsToModify.isPresent()) {
               Firestations firestations = firestationsToModify.get();
               firestations.setStation(firestationModified.getStation());
            }


            JsonFileHandler.writeJsonFile(jsonFile);

        } catch (Exception e) {
            logger.error("La modification à rencontrer un problème: {}", e.getMessage());
        }

    }

    public void deleteStation(Firestations firestationsToDelete) throws IOException, FiretationNotFoundException {

        DataJsonHandler jsonFile = JsonFileHandler.readJsonFile();
        List<Firestations> firestationsList = jsonFile.getFirestations();

        boolean isRemoved = firestationsList.removeIf(firestations ->
                firestations.getAddress().equalsIgnoreCase(firestationsToDelete.getAddress()) &&
                firestations.getStation().equalsIgnoreCase(firestationsToDelete.getStation())
        );

        if (!isRemoved) {
            throw new FiretationNotFoundException("FireStation introuvable avec le prénom et le nom fournis.");
        }

        JsonFileHandler.writeJsonFile(jsonFile);

    }
}
