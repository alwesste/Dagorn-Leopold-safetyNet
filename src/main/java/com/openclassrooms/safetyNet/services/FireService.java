package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestations;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.models.Persons;
import com.openclassrooms.safetyNet.result.FireHabitant;
import com.openclassrooms.safetyNet.result.MedicalHistory;
import com.openclassrooms.safetyNet.result.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FireService {

    @Autowired
    JsonFileHandler jsonFileHandler;

    public FireHabitant getFireHabitantByAdress(String address) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();

        List<Persons> personsAtAddress = jsonFile.getPersons().stream()
                .filter(person -> person.getAddress().equalsIgnoreCase(address))
                .toList();

        String stationNumber = jsonFile.getFirestations().stream()
                .filter(fs -> fs.getAddress().equalsIgnoreCase(address))
                .map(Firestations::getStation)
                .findFirst()
                .orElse(null);

        List<PersonDetails> personDetailsList = personsAtAddress.stream()
                .map(person -> {
                    Optional<MedicalRecords> medicalRecord = jsonFile.getMedicalrecords().stream()
                            .filter(mr -> mr.getFirstName().equalsIgnoreCase(person.getFirstName())
                                    && mr.getLastName().equalsIgnoreCase(person.getLastName()))
                            .findFirst();

                    int age = medicalRecord
                            .map(mr -> calculateAge(mr.getBirthdate()))
                            .orElse(0);

                    List<MedicalHistory> medicalHistories = medicalRecord
                            .map(mr -> List.of(new MedicalHistory(mr.getMedications(), mr.getAllergies())))
                            .orElse(new ArrayList<>());

                    return new PersonDetails(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getPhone(),
                            age,
                            medicalHistories,
                            stationNumber
                    );
                })
                .toList();

        return new FireHabitant(personDetailsList);
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
