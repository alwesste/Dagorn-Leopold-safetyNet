package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.exceptions.FirestationNotFoundException;
import com.openclassrooms.safetyNet.interfaces.IFireStationService;
import com.openclassrooms.safetyNet.interfaces.IJsonFileHandler;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestations;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.models.Persons;
import com.openclassrooms.safetyNet.result.PhoneNumber;
import com.openclassrooms.safetyNet.result.StationCover;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class FirestationsService implements IFireStationService {

    private static final Logger logger = LogManager.getLogger(FirestationsService.class);

    @Autowired
    private IJsonFileHandler jsonFileHandler;

    @Autowired
    CalculateAgeService calculateAgeService;

    @Override
    public List<StationCover> getCoverPersons(int stationNumber) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();

        List<Firestations> fireStationList = jsonFile.getFirestations();
        List<Persons> personsList = jsonFile.getPersons();
        List<MedicalRecords> medicalRecordsList = jsonFile.getMedicalrecords();
        List<String> addresses = getAddressesByStation(fireStationList, stationNumber);
        List<Persons> persons = getPersonsByAddresses(personsList, addresses);
        List<StationCover> stationCoverList = new ArrayList<>();

        int adultsCount = 0;
        int childrenCount = 0;

        for (Persons person : persons) {
            Optional<MedicalRecords> medicalRecord = medicalRecordsList.stream()
                    .filter(mr -> mr.getFirstName().equalsIgnoreCase(person.getFirstName()) && mr.getLastName().equalsIgnoreCase(person.getLastName()))
                    .findFirst();

            int age = medicalRecord.map(mr -> calculateAgeService.calculateAge(mr.getBirthdate())).orElse(0);


            if (age >= 18) {
                adultsCount++;
            } else {
                childrenCount++;
            }
        }

        for (Persons person : persons) {
            Optional<MedicalRecords> medicalRecord = medicalRecordsList.stream()
                    .filter(mr -> mr.getFirstName().equalsIgnoreCase(person.getFirstName()) && mr.getLastName().equalsIgnoreCase(person.getLastName()))
                    .findFirst();

            int age = medicalRecord.map(mr -> calculateAgeService.calculateAge(mr.getBirthdate())).orElse(0);

            stationCoverList.add(new StationCover(
                    person.getFirstName(),
                    person.getLastName(),
                    person.getAddress(),
                    person.getPhone(),
                    age,
                    adultsCount,
                    childrenCount
            ));
        }

        return stationCoverList;
    }


    @Override
    public List<String> getAddressesByStation(List<Firestations> fireStations, int stationNumber) {
        return fireStations.stream()
                .filter(fs -> fs.getStation().equals(String.valueOf(stationNumber)))
                .map(Firestations::getAddress)
                .toList();
    }

    @Override
    public List<Persons> getPersonsByAddresses(List<Persons> persons, List<String> addresses) {
        return persons.stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .toList();
    }

    @Override
    public List<String> getAddressfromStationNumber(int firestation) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        return jsonFile.getFirestations().stream()
                .filter(sa -> sa.getStation().equals(String.valueOf(firestation)))
                .map(Firestations::getAddress)
                .toList();
    }

    @Override
    public List<PhoneNumber> getPhoneFromAddressFromStationNumber(List<String> stationAddress) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        return jsonFile.getPersons().stream()
                .filter(pn -> stationAddress.contains(pn.getAddress()))
                .map(pn -> new PhoneNumber(pn.getPhone()))
                .distinct()
                .toList();
    }

    @Override
    public void addFireStation(Firestations newFirestations) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<Firestations> fireStationList = jsonFile.getFirestations();

        fireStationList.add(newFirestations);
        jsonFileHandler.writeJsonFile(jsonFile);
    }

    @Override
    public void modifyFireStation(Firestations firestationModified) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<Firestations> fireStationList = jsonFile.getFirestations();

        Optional<Firestations> oFirestationsToModify = fireStationList.stream()
                .filter(firestations -> firestations.getAddress().equalsIgnoreCase(firestationModified.getAddress()))
                .findFirst();


        if (oFirestationsToModify.isPresent()) {
            Firestations firestations = oFirestationsToModify.get(); //orElseGet()
            firestations.setStation(firestationModified.getStation());
        }

        jsonFileHandler.writeJsonFile(jsonFile);

    }

    @Override
    public void deleteStation(Firestations firestationsToDelete) throws IOException, FirestationNotFoundException {

        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<Firestations> firestationsList = jsonFile.getFirestations();

        boolean isRemoved = firestationsList.removeIf(firestations ->
                firestations.getAddress().equalsIgnoreCase(firestationsToDelete.getAddress()) &&
                        firestations.getStation().equalsIgnoreCase(firestationsToDelete.getStation())
        );

        if (!isRemoved) {
            throw new FirestationNotFoundException("FireStation introuvable avec le prénom et le nom fournis.");
        }

        jsonFileHandler.writeJsonFile(jsonFile);

    }
}
