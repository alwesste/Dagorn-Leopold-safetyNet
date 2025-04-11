package com.openclassrooms.safetyNet.services.impl;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.result.MedicalHistory;
import com.openclassrooms.safetyNet.result.PersonInfoLastnameDetail;
import com.openclassrooms.safetyNet.services.IJsonFileHandler;
import com.openclassrooms.safetyNet.services.IPersonLastNameSercice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonLastNameSercice implements IPersonLastNameSercice {

    private final Logger logger = LogManager.getLogger(PersonLastNameSercice.class);

    @Autowired
    IJsonFileHandler jsonFileHandler;

    @Autowired
    CalculateAgeService calculateAgeService;

    @Override
    public List<PersonInfoLastnameDetail> getPersonInfoFromLastName(String lastName) throws IOException {
        DataJsonHandler jsonfile = jsonFileHandler.readJsonFile();
        logger.debug("getPersonInfoFromLastName name : {} ", lastName);

        return jsonfile.getPersons().stream()
                .filter(persons -> persons.getLastName().equalsIgnoreCase(lastName))
                .map(person -> {
                    Optional<MedicalRecords> medicalRecord = jsonfile.getMedicalrecords().stream()
                            .filter(record -> record.getFirstName().equalsIgnoreCase(person.getFirstName())
                                    && record.getLastName().equalsIgnoreCase(person.getLastName()))
                            .findFirst();
                    logger.debug("medicalRecord obtenu a partir du firstname et lastname: {}", medicalRecord);


                    List<MedicalHistory> medicalHistories = medicalRecord
                            .map(mr -> {
                                List<MedicalHistory> histories = new ArrayList<>();
                                if (!mr.getMedications().isEmpty() || !mr.getAllergies().isEmpty()) {
                                    histories.add(new MedicalHistory(mr.getMedications(), mr.getAllergies()));
                                }
                                logger.debug("Conditon medical retourne {}, pour {}", histories, person.getFirstName());
                                return histories.isEmpty() ? null : histories;
                            })
                            .orElse(null);
                    int age = medicalRecord
                            .map(mr -> calculateAgeService.calculateAge(mr.getBirthdate()))
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
    }
}
