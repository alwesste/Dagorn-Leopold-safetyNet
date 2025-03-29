package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestations;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.result.FloodHabitant;
import com.openclassrooms.safetyNet.result.MedicalHistory;
import com.openclassrooms.safetyNet.utils.JsonFileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FloodService {

    @Autowired
    JsonFileHandler jsonFileHandler;

    @Autowired
    CalculateAgeService calculateAgeService;

    public List<FloodHabitant> getHomeByStation(List<String> stations) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();

        List<String> addressFireStation = jsonFile.getFirestations().stream()
                .filter(station -> stations.contains(station.getStation()))
                .map(Firestations::getAddress)
                .toList();


        return jsonFile.getPersons().stream()
                .filter(persons -> addressFireStation.contains(persons.getAddress()))
                .map(person -> {
                    Optional<MedicalRecords> medicalRecord = jsonFile.getMedicalrecords().stream()
                            .filter(mr -> mr.getFirstName().equalsIgnoreCase(person.getFirstName())
                                    && mr.getLastName().equalsIgnoreCase(person.getLastName()))
                            .findFirst();

                    int age = medicalRecord
                            .map(mr -> calculateAgeService.calculateAge(mr.getBirthdate()))
                            .orElse(0);

                    List<MedicalHistory> medicalHistories = medicalRecord
                            .map(mr -> List.of(new MedicalHistory(mr.getMedications(), mr.getAllergies())))
                            .orElse(new ArrayList<>());

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