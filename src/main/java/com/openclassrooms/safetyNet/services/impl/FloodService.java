package com.openclassrooms.safetyNet.services.impl;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestation;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.result.FloodHabitant;
import com.openclassrooms.safetyNet.result.MedicalHistory;
import com.openclassrooms.safetyNet.services.IFloodService;
import com.openclassrooms.safetyNet.services.IJsonFileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FloodService implements IFloodService {

    private final Logger logger= LogManager.getLogger(FloodService.class);

    @Autowired
    IJsonFileHandler jsonFileHandler;

    @Autowired
    CalculateAgeService calculateAgeService;

    @Override
    public List<FloodHabitant> getHomeByStation(List<String> stations) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();

        List<String> addressFireStation = jsonFile.getFirestations().stream()
                .filter(station -> stations.contains(station.getStation()))
                .map(Firestation::getAddress)
                .toList();
        logger.debug("Adresse recuperer par adresse adresseFireStation: {}", addressFireStation);


        return jsonFile.getPersons().stream()
                .filter(persons -> addressFireStation.contains(persons.getAddress()))
                .map(person -> {
                    Optional<MedicalRecords> medicalRecord = jsonFile.getMedicalrecords().stream()
                            .filter(mr -> mr.getFirstName().equalsIgnoreCase(person.getFirstName())
                                    && mr.getLastName().equalsIgnoreCase(person.getLastName()))
                            .findFirst();
                    logger.debug("MedicalRecord obtenu : {} ", medicalRecord);

                    int age = medicalRecord
                            .map(mr -> calculateAgeService.calculateAge(mr.getBirthdate()))
                            .orElse(0);

                    List<MedicalHistory> medicalHistories = medicalRecord
                            .map(mr -> List.of(new MedicalHistory(mr.getMedications(), mr.getAllergies())))
                            .orElse(new ArrayList<>());
                    logger.debug("MedicalHistory obtenu : {} ", medicalHistories);

                    return new FloodHabitant(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getPhone(),
                            age,
                            medicalHistories
                    );
                })
                .toList();
    }
}