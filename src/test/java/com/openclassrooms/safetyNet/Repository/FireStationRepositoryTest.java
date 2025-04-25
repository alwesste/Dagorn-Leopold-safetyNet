package com.openclassrooms.safetyNet.Repository;

import com.openclassrooms.safetyNet.SafetyNetApplication;
import com.openclassrooms.safetyNet.exceptions.FirestationNotFoundException;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestation;
import com.openclassrooms.safetyNet.models.MedicalRecord;
import com.openclassrooms.safetyNet.models.Person;
import com.openclassrooms.safetyNet.repository.FirestationRepository;
import com.openclassrooms.safetyNet.services.impl.CalculateAgeService;
import com.openclassrooms.safetyNet.repository.JsonFileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SafetyNetApplication.class)
@ExtendWith(MockitoExtension.class)
public class FireStationRepositoryTest {

    @Mock
    private JsonFileHandler jsonFileHandler;

    @Mock
    private CalculateAgeService calculateAgeService;

    @InjectMocks
    private FirestationRepository firestationRepository;

    DataJsonHandler mockdataJsonHandler;

    @BeforeEach
    void setUp() throws IOException {
        mockdataJsonHandler = new DataJsonHandler();

        Firestation firestations1 = new Firestation("1509 Culver St", "1");
        Firestation firestations2 = new Firestation("456 Oak St", "2");
        List<Firestation> firestationsList = new ArrayList<>(List.of(firestations1, firestations2));

        Person personJohn = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "John@email.com");
        Person personClara = new Person("Clara", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "Clara@email.com");
        Person personPaul = new Person("Paul", "steph", "1509 Culver St", "Culver", "97451", "841-874-6512", "Paul@email.com");
        Person personSophie = new Person("Sophie", "Rodrigez", "1509 Culver St", "Culver", "97451", "841-874-6512", "Sophie@email.com");
        Person personCharle = new Person("Charle", "Pastoi", "1509 Culver St", "Culver", "97451", "841-874-6512", "Charle@email.com");
        List<Person> personsList = new ArrayList<>(List.of(personJohn, personClara, personPaul, personSophie, personCharle));

        MedicalRecord medicalRecordsOfJohn = new MedicalRecord("John","Boyd","03/06/2020",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Collections.emptyList()
        );
        MedicalRecord medicalRecordsOfClara = new MedicalRecord("Clara","Boyd","04/11/2015",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                List.of("allergies:illisoxian")
        );
        MedicalRecord medicalRecordsOfPaul = new MedicalRecord("Paul","steph","03/06/1980",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                List.of("allergies:illisoxian")
        );
        MedicalRecord medicalRecordsOfSophie = new MedicalRecord("Sophie","Rodrigez","03/06/1999",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                List.of("allergies:illisoxian")
        );
        MedicalRecord medicalRecordsOfCharle = new MedicalRecord("Charle","Pastoi","03/06/2000",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                List.of("allergies:illisoxian")
        );

        mockdataJsonHandler.setFirestations(firestationsList);
        mockdataJsonHandler.setPersons(personsList);
        mockdataJsonHandler.setMedicalrecords(List.of(medicalRecordsOfCharle, medicalRecordsOfClara, medicalRecordsOfJohn, medicalRecordsOfSophie, medicalRecordsOfPaul));
        when(jsonFileHandler.readJsonFile()).thenReturn(mockdataJsonHandler);
    }

    @Test
    public void addFireStationTest() throws IOException {
        Firestation firestationsToAdd = new Firestation("111 test", "3");

        firestationRepository.addFireStation(firestationsToAdd);
        List<Firestation> allFirestations = jsonFileHandler.readJsonFile().getFirestations();

        assertNotNull(allFirestations, "La liste des casernes n'est pas null");
        assertTrue(allFirestations.contains(firestationsToAdd), "La caserne n'a pas été ajoutée correctement.");

        Firestation addedFirestation = allFirestations.stream()
                .filter(firestation -> "111 test".equals(firestation.getAddress()) && "3".equals(firestation.getStation()))
                .findFirst()
                .orElse(null);

        assertNotNull(addedFirestation, "La caserne ajoutée devrait être présente.");
        assertEquals("111 test", addedFirestation.getAddress(), "L'adresse de la caserne ajoutée est incorrecte.");
        assertEquals("3", addedFirestation.getStation(), "Le numéro de la station ajouté est incorrect.");
    }

    @Test
    public void modifyFireStationTest() throws IOException {

        Firestation newfireStation = new Firestation("456 Oak St", "3");
        firestationRepository.modifyFireStation(newfireStation);

        List<Firestation> allFirestations = jsonFileHandler.readJsonFile().getFirestations();

        assertNotNull(allFirestations, "La liste des casernes ne doit pas être nulle.");

        assertFalse(allFirestations.stream()
                        .anyMatch(firestation -> "456 Oak St".equals(firestation.getAddress()) && "2".equals(firestation.getStation())),
                "L'ancienne station 2 ne doit plus exister a cette adresse."
        );

        assertTrue(allFirestations.stream()
                        .anyMatch(firestation -> firestation.getAddress().equalsIgnoreCase("456 Oak St") && firestation.getStation().equalsIgnoreCase("3")),
                "La station doit être mise à jour avec le numéro 3."
        );
    }

    @Test
    public void deleteStationTest() throws IOException, FirestationNotFoundException {

        Firestation deleletedStation = new Firestation("1509 Culver St", "1");
        firestationRepository.deleteFireStation(deleletedStation);

        List<Firestation> allFirestations = jsonFileHandler.readJsonFile().getFirestations();

        assertFalse(allFirestations.contains(deleletedStation), "La caserne n'a pas été supprimer correctement.");

    }
}
