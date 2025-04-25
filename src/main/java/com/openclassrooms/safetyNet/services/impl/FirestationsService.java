package com.openclassrooms.safetyNet.services.impl;

import com.openclassrooms.safetyNet.exceptions.FirestationNotFoundException;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestation;
import com.openclassrooms.safetyNet.models.MedicalRecord;
import com.openclassrooms.safetyNet.models.Person;
import com.openclassrooms.safetyNet.repository.FirestationRepository;
import com.openclassrooms.safetyNet.result.PhoneNumber;
import com.openclassrooms.safetyNet.result.StationCover;
import com.openclassrooms.safetyNet.services.IFireStationService;
import com.openclassrooms.safetyNet.services.IJsonFileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


@Service
public class FirestationsService implements IFireStationService {

    @Autowired
    FirestationRepository firestationRepository;

    private final Logger logger = LogManager.getLogger(FirestationRepository.class);

    @Autowired
    private IJsonFileHandler jsonFileHandler;

    @Autowired
    CalculateAgeService calculateAgeService;

    @Override
    public List<StationCover> getCoverPersons(int stationNumber) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();

        List<Firestation> fireStationList = jsonFile.getFirestations();
        List<Person> personsList = jsonFile.getPersons();
        List<MedicalRecord> medicalRecordsList = jsonFile.getMedicalrecords();
        List<String> addresses = getAddressesByStation(fireStationList, stationNumber);
        List<Person> persons = getPersonsByAddresses(personsList, addresses);
        List<StationCover> stationCoverList = new ArrayList<>();

        int adultsCount = 0;
        int childrenCount = 0;

        for (Person person : persons) {
            Optional<MedicalRecord> medicalRecord = medicalRecordsList.stream()
                    .filter(mr -> mr.getFirstName().equalsIgnoreCase(person.getFirstName()) && mr.getLastName().equalsIgnoreCase(person.getLastName()))
                    .findFirst();

            int age = medicalRecord.map(mr -> calculateAgeService.calculateAge(mr.getBirthdate())).orElse(0);
            logger.debug("Age calcule sur les medicalRecord : {}", age);


            if (age >= 18) {
                adultsCount++;
            } else {
                childrenCount++;
            }
        }

        for (Person person : persons) {
            Optional<MedicalRecord> medicalRecord = medicalRecordsList.stream()
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
    public List<String> getAddressesByStation(List<Firestation> fireStations, int stationNumber) {
        logger.debug("Liste des fireStation {}, et numero de station: {}", fireStations, stationNumber);

        List<String> address = fireStations.stream()
                .filter(fs -> fs.getStation().equals(String.valueOf(stationNumber)))
                .map(Firestation::getAddress)
                .toList();
        logger.debug("Liste des differente adresse retrouver par numero de station : {}", address);

        return address;
    }

    @Override
    public List<Person> getPersonsByAddresses(List<Person> persons, List<String> addresses) {
        logger.debug("Nombre de liste de person: {}, et nombre de liste d'adresse: {}", persons.size(), addresses.size());

        List<Person> personsList = persons.stream()
                .filter(person -> addresses.contains(person.getAddress()))

                .toList();
        logger.debug("Liste des differente personnes retrouver par adresse: {}", personsList);

        return personsList;
    }

    @Override
    public List<String> getAddressfromStationNumber(int firestation) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        logger.debug("Liste des numeros des fireStation {}", firestation);

        List<String> address = jsonFile.getFirestations().stream()
                .filter(sa -> sa.getStation().equals(String.valueOf(firestation)))
                .map(Firestation::getAddress)
                .toList();

        logger.debug("Liste des differente adresse retrouver par firestation : {}", address);

        return address;
    }

    @Override
    public List<PhoneNumber> getPhoneFromAddressFromStationNumber(List<String> stationAddress) throws IOException {
        DataJsonHandler jsonFile = jsonFileHandler.readJsonFile();
        logger.debug("Liste des adresses recupere {}", stationAddress);

        Set<String> uniquePhones = new HashSet<>();

        List<PhoneNumber> phoneNumbers = jsonFile.getPersons().stream()
                .filter(pn -> stationAddress.contains(pn.getAddress()))
                .map(Person::getPhone)
                .filter(uniquePhones::add)
                .map(PhoneNumber::new)
                .toList();
        logger.debug("Liste des numeros de telephone: {}", phoneNumbers);

        return phoneNumbers;
    }

    @Override
    public void addFireStation(Firestation newFirestation) throws IOException {
        firestationRepository.addFireStation(newFirestation);
    }

    @Override
    public void modifyFireStation(Firestation firestationModified) throws IOException {
        firestationRepository.modifyFireStation(firestationModified);
    }

    @Override
    public void deleteStation(Firestation firestationToDelete) throws IOException, FirestationNotFoundException {
        firestationRepository.deleteFireStation(firestationToDelete);
    }
}
