package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestation;
import com.openclassrooms.safetyNet.models.MedicalRecord;
import com.openclassrooms.safetyNet.models.Person;
import com.openclassrooms.safetyNet.result.StationCover;
import com.openclassrooms.safetyNet.services.impl.CalculateAgeService;
import com.openclassrooms.safetyNet.services.impl.FirestationsService;
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
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @Mock
    private JsonFileHandler jsonFileHandler;

    @Mock
    private CalculateAgeService calculateAgeService;

    @InjectMocks
    private FirestationsService firestationsService;

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
    public void getCoverPersonTest() throws IOException {

        when(calculateAgeService.calculateAge("03/06/2020")).thenReturn(5);
        when(calculateAgeService.calculateAge("04/11/2015")).thenReturn(10);
        when(calculateAgeService.calculateAge("03/06/1980")).thenReturn(41);
        when(calculateAgeService.calculateAge("03/06/1999")).thenReturn(22);
        when(calculateAgeService.calculateAge("03/06/2000")).thenReturn(21);

        List<StationCover> result = firestationsService.getCoverPersons(1);

        assertNotNull(result);
        System.out.println(result);
        assertEquals(3, result.getFirst().getAdultsCount());
        assertEquals(2, result.getFirst().getChildrenCount());

        StationCover stationCover = result.stream()
                .filter(sc -> sc.getFirstName().equals("John") && sc.getLastName().equals("Boyd"))
                .findFirst()
                .orElse(null);

        assertNotNull(stationCover, "John Boyd devrait être dans la liste.");
        assertEquals("1509 Culver St", stationCover.getAddress());
        assertEquals("841-874-6512", stationCover.getPhone());
        assertEquals(5, stationCover.getAge());
    }
}