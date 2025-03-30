package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.interfaces.IChildAlertService;
import com.openclassrooms.safetyNet.interfaces.IJsonFileHandler;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.models.Persons;
import com.openclassrooms.safetyNet.result.ChildAlert;
import com.openclassrooms.safetyNet.result.FamilyMember;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ChildAlertService implements IChildAlertService {

    private CalculateAgeService calculateAgeService;

    private IJsonFileHandler jsonFileHandler;

    public ChildAlertService(IJsonFileHandler jsonFileHandler, CalculateAgeService calculateAgeService) {
        this.jsonFileHandler = jsonFileHandler;
        this.calculateAgeService = calculateAgeService;
    }


    @Override
    public List<ChildAlert> getListOfChild(String address) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();

        List<Persons> personByAddress = jsonFile.getPersons().stream().filter(person -> person.getAddress().equalsIgnoreCase(address)).toList();

        List<ChildAlert> children = personByAddress.stream().map(persons -> {
            MedicalRecords medicalrecord = jsonFile.getMedicalrecords().stream().filter(mr -> mr.getFirstName().equalsIgnoreCase(persons.getFirstName()) && mr.getLastName().equalsIgnoreCase(persons.getLastName())).findFirst().orElse(null);

            if (medicalrecord != null) {
                int age = calculateAgeService.calculateAge((medicalrecord.getBirthdate()));

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
