package com.openclassrooms.safetyNet.services.impl;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.MedicalRecord;
import com.openclassrooms.safetyNet.models.Person;
import com.openclassrooms.safetyNet.result.ChildAlert;
import com.openclassrooms.safetyNet.result.FamilyMember;
import com.openclassrooms.safetyNet.services.IChildAlertService;
import com.openclassrooms.safetyNet.services.IJsonFileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ChildAlertService implements IChildAlertService {

    private final Logger logger = LogManager.getLogger(ChildAlertService.class);

    private CalculateAgeService calculateAgeService;

    private IJsonFileHandler jsonFileHandler;

    public ChildAlertService(IJsonFileHandler jsonFileHandler, CalculateAgeService calculateAgeService) {
        this.jsonFileHandler = jsonFileHandler;
        this.calculateAgeService = calculateAgeService;
    }

    @Override
    public List<ChildAlert> getListOfChild(String address) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        logger.debug("Donnees lues par jsonFileHandler,  nombre de personnes récupérées : {}", jsonFile.getPersons().size());

        List<Person> personByAddress = jsonFile.getPersons().stream().filter(person -> person.getAddress().equalsIgnoreCase(address)).toList();
        logger.debug("Nombre des personne par adresse {}", personByAddress.size());

        List<ChildAlert> children = personByAddress.stream().map(persons -> {
            MedicalRecord medicalrecord = jsonFile.getMedicalrecords().stream().filter(mr -> mr.getFirstName().equalsIgnoreCase(persons.getFirstName()) && mr.getLastName().equalsIgnoreCase(persons.getLastName())).findFirst().orElse(null);

            if (medicalrecord != null) {
                logger.debug("Medical Record de : {}, {}, ", medicalrecord.getLastName(), medicalrecord.getFirstName());

                int age = calculateAgeService.calculateAge((medicalrecord.getBirthdate()));
                logger.debug("Age recuperer par date d'anniversaire dans les medicalRecord {}", age);

                if (age < 18) {
                    List<FamilyMember> familyMembers = personByAddress.stream().filter(family -> !family.getFirstName().equalsIgnoreCase(persons.getFirstName())).map(family -> new FamilyMember(family.getFirstName(), family.getLastName())).toList();

                    return new ChildAlert(persons.getFirstName(), familyMembers, age, persons.getLastName());
                }
            }
            return null;
        }).filter(Objects::nonNull).toList();

        return children.isEmpty() ? null : children;
    }
}
