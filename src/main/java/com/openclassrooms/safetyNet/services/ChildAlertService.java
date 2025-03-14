package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.models.*;
import com.openclassrooms.safetyNet.result.ChildAlert;
import com.openclassrooms.safetyNet.result.ChildInformation;
import com.openclassrooms.safetyNet.result.FamilyMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ChildAlertService {

    @Autowired
    JsonFileHandler jsonFileHandler;

    public ChildAlert getListOfChild(String address) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();

        List<Persons> personByAddress = jsonFile.getPersons().stream()
                .filter(person -> person.getAddress().equalsIgnoreCase(address))
                .toList();

        List<ChildInformation> children = personByAddress.stream()
                .map(person -> getChildInfo(person, jsonFile.getMedicalrecords()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        List<FamilyMember> famillyMember = jsonFile.getPersons().stream()
                .filter(fm -> fm.getAddress().equalsIgnoreCase(address))
                .filter(fm -> children.stream()
                        .noneMatch(child -> child.getFirstName().equalsIgnoreCase(fm.getFirstName()) &&
                                child.getLastName().equalsIgnoreCase(fm.getLastName())))
                .map(fm -> new FamilyMember(fm.getFirstName(), fm.getLastName()))
                .toList();

        if (children.isEmpty()) {
            return null;
        } else {
            return new ChildAlert(children, famillyMember);
        }
    }

    private Optional<ChildInformation> getChildInfo(Persons person, List<MedicalRecords> medicalRecordsList) {
        return medicalRecordsList.stream()
                .filter(mr -> mr.getFirstName().equalsIgnoreCase(person.getFirstName()) &&
                        mr.getLastName().equalsIgnoreCase(person.getLastName()))
                .findFirst()
                .filter(mr -> calculateAge(mr.getBirthdate()) <= 18)
                .map(mr -> new ChildInformation(person.getFirstName(), person.getLastName(), calculateAge(mr.getBirthdate())));
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate birthDate = LocalDate.parse(birthdate, formatter);
            return Period.between(birthDate, LocalDate.now()).getYears();
    }


}
