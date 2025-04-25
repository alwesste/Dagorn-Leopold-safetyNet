package com.openclassrooms.safetyNet.services.impl;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestation;
import com.openclassrooms.safetyNet.models.MedicalRecord;
import com.openclassrooms.safetyNet.models.Person;
import com.openclassrooms.safetyNet.result.FireHabitantDetails;
import com.openclassrooms.safetyNet.result.MedicalHistory;
import com.openclassrooms.safetyNet.services.IFireService;
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
public class FireService implements IFireService {

    private final Logger logger = LogManager.getLogger(FireService.class);

    @Autowired
    IJsonFileHandler jsonFileHandler;

    @Autowired
    CalculateAgeService calculateAgeService;

    @Override
    public List<FireHabitantDetails> getFireHabitantByAdress(String address) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();

        List<Person> personsAtAddress = jsonFile.getPersons().stream()
                .filter(person -> person.getAddress().equalsIgnoreCase(address))
                .toList();
        logger.debug("Noms des personnes à l'adresse {}: {}", address, personsAtAddress);


        String stationNumber = jsonFile.getFirestations().stream()
                .filter(fs -> fs.getAddress().equalsIgnoreCase(address))
                .map(Firestation::getStation)
                .findFirst()
                .orElse(null);
        logger.debug("Numero de station service recupere: {}", stationNumber);

        return personsAtAddress.stream()
                .map(person -> {
                    Optional<MedicalRecord> medicalRecord = jsonFile.getMedicalrecords().stream()
                            .filter(mr -> mr.getFirstName().equalsIgnoreCase(person.getFirstName())
                                    && mr.getLastName().equalsIgnoreCase(person.getLastName()))
                            .findFirst();
                    logger.debug("Prenon et nom des medical record recuperer: {} {}", person.getFirstName(), person.getLastName());

                    int age = medicalRecord
                            .map(mr -> calculateAgeService.calculateAge(mr.getBirthdate()))
                            .orElse(0);
                    logger.debug("Age recuperer {} pour {}", age, person.getFirstName());

                    List<MedicalHistory> medicalHistories = medicalRecord
                            .map(mr -> List.of(new MedicalHistory(mr.getMedications(), mr.getAllergies())))
                            .orElse(new ArrayList<>());
                    logger.debug("Antecedent medicaux pour : {}  Medecine : {}  Allergie : {}",
                            person.getFirstName(),
                            medicalHistories.stream().map(MedicalHistory::getMedicine).toList(),
                            medicalHistories.stream().map(MedicalHistory::getAllergie).toList()
                    );

                    return new FireHabitantDetails(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getPhone(),
                            age,
                            medicalHistories,
                            stationNumber
                    );
                })
                .toList();
    }
}
