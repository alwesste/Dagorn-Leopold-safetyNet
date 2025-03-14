package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.result.MedicalHistory;
import com.openclassrooms.safetyNet.result.PersonInfoLastname;
import com.openclassrooms.safetyNet.result.PersonInfoLastnameDetail;
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
public class PersonLastNameSercice {

    @Autowired
    JsonFileHandler jsonFileHandler;

    public PersonInfoLastname getPersonInfoFromLastName(String lastName) throws IOException {
        DataJsonHandler jsonfile = jsonFileHandler.readJsonFile();

        List<PersonInfoLastnameDetail> personInfoLastnameDetails = jsonfile.getPersons().stream()
                .filter(persons -> persons.getLastName().equalsIgnoreCase(lastName))
                .map(person -> {
                    Optional<MedicalRecords> medicalRecord = jsonfile.getMedicalrecords().stream()
                            .filter(record -> record.getFirstName().equalsIgnoreCase(person.getFirstName())
                                    && record.getLastName().equalsIgnoreCase(person.getLastName()))
                            .findFirst();

                    List<MedicalHistory> medicalHistories = medicalRecord
                            .map(mr -> {
                                List<MedicalHistory> histories = new ArrayList<>();
                                if (!mr.getMedications().isEmpty() || !mr.getAllergies().isEmpty()) {
                                    histories.add(new MedicalHistory(mr.getMedications(), mr.getAllergies()));
                                }
                                return histories.isEmpty() ? null : histories; // Renvoie null si vide
                            })
                            .orElse(null);
                    int age = medicalRecord
                            .map(mr -> calculateAge(mr.getBirthdate()))
                            .orElse(0);

                    return new PersonInfoLastnameDetail(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getAddress(),
                            age,
                            person.getEmail(),
                            medicalHistories
                    );
                })
                .toList();

        return new PersonInfoLastname(personInfoLastnameDetails);

    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
