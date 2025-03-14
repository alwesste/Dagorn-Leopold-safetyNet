package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.exceptions.FirestationNotFoundException;
import com.openclassrooms.safetyNet.models.*;
import com.openclassrooms.safetyNet.result.PersonInformation;
import com.openclassrooms.safetyNet.result.PhoneNumber;
import com.openclassrooms.safetyNet.result.StationCover;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class FirestationsService {

    private static final Logger logger = LogManager.getLogger(FirestationsService.class);

    @Autowired
    private JsonFileHandler jsonFileHandler;

    public StationCover getCoverPersons(int stationNumber) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<Firestations> fireStationList = jsonFile.getFirestations();
        List<Persons> personsList = jsonFile.getPersons();
        List<MedicalRecords> medicalRecordsList = jsonFile.getMedicalrecords();

        // 1. Récupérer les adresses par numéro de station
        List<String> addresses = getAddressesByStation(fireStationList, stationNumber);

        // 2. Récupérer les personnes par adresses
        List<Persons> persons = getPersonsByAddresses(personsList, addresses);

        // 3. Créer la liste avec toutes les informations et compter les adultes/enfants
        List<PersonInformation> personInfos = new ArrayList<>();
        int adultsCount = 0;
        int childrenCount = 0;

        for (Persons person : persons) {
            int age = calculateAge(person, medicalRecordsList);
            personInfos.add(new PersonInformation(
                    person.getFirstName(),
                    person.getLastName(),
                    person.getAddress(),
                    person.getPhone(),
                    age
            ));

            if (age >= 18) {
                adultsCount++;
            } else {
                childrenCount++;
            }
        }

        return new StationCover(personInfos, adultsCount, childrenCount);
    }

    // 1. Fonction pour récupérer les adresses par numéro de station
    public List<String> getAddressesByStation(List<Firestations> fireStations, int stationNumber) {
        return fireStations.stream()
                .filter(fs -> fs.getStation().equals(String.valueOf(stationNumber)))
                .map(Firestations::getAddress)
                .toList();
    }

    // 2. Fonction pour récupérer les personnes par adresses
    public List<Persons> getPersonsByAddresses(List<Persons> persons, List<String> addresses) {
        return persons.stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .toList();
    }

    // 3. Fonction pour calculer l'âge à partir du prénom et nom
    public int calculateAge(Persons person, List<MedicalRecords> medicalRecords) {
        return medicalRecords.stream()
                .filter(mr -> mr.getFirstName().equalsIgnoreCase(person.getFirstName()) && mr.getLastName().equalsIgnoreCase(person.getLastName()))
                .findFirst()
                .map(mr -> {
                    try {
                        LocalDate birthDate = LocalDate.parse(mr.getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                        return Period.between(birthDate, LocalDate.now()).getYears();
                    } catch (Exception e) {
                        logger.error("Erreur dans le calcu de l'age de : " + person.getFirstName() + " " + person.getLastName(), e);
                        return 0;
                    }
                })
                .orElse(0);
    }

    public List<String> getAddressfromStationNumber(int firestation) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        return  jsonFile.getFirestations().stream()
                .filter(sa -> sa.getStation().equals(String.valueOf(firestation)))
                .map(Firestations::getAddress)
                .toList();
    }

    public List<PhoneNumber> getPhoneFromAddressFromStationNumber(List<String> stationAddress) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        return jsonFile.getPersons().stream()
                .filter(pn -> stationAddress.contains(pn.getAddress()))
                .map(pn -> new PhoneNumber(pn.getPhone()))
                .distinct()
                .toList();
    }


    public void addFireStation(Firestations newFirestations) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<Firestations> fireStationList = jsonFile.getFirestations();

        fireStationList.add(newFirestations);
        jsonFileHandler.writeJsonFile(jsonFile);
    }


    public void modifyFireStation(Firestations firestationModified) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        List<Firestations> fireStationList = jsonFile.getFirestations();

        Optional<Firestations> oFirestationsToModify = fireStationList.stream()
                .filter(firestations -> firestations.getAddress().equalsIgnoreCase(firestationModified.getAddress()))
                .findFirst();

//            oFirestationsToModify.ifPresent((f) -> {f.setStation(firestationModified.getStation());
//            });

        if (oFirestationsToModify.isPresent()) {
            Firestations firestations = oFirestationsToModify.get(); //orElseGet()
            firestations.setStation(firestationModified.getStation());
        }

        jsonFileHandler.writeJsonFile(jsonFile);

    }

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
