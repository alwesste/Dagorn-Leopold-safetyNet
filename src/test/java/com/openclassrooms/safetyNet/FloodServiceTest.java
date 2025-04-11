package com.openclassrooms.safetyNet;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestation;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.models.Persons;
import com.openclassrooms.safetyNet.result.FloodHabitant;
import com.openclassrooms.safetyNet.services.impl.CalculateAgeService;
import com.openclassrooms.safetyNet.services.impl.FloodService;
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

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FloodServiceTest {

    @Mock
    JsonFileHandler jsonFileHandler;

    @Mock
    CalculateAgeService calculateAgeService;

    @InjectMocks
    FloodService floodService;

    DataJsonHandler mockDataJsonFileHandler;

    @BeforeEach
    void setUp() throws IOException {
        mockDataJsonFileHandler = new DataJsonHandler();

        Persons personJohn = new Persons("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "John@email.com");
        Persons personClara = new Persons("Clara", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "Clara@email.com");
        Persons personPaul = new Persons("Paul", "steph", "456 Oak St", "Culver", "97451", "841-874-6512", "Paul@email.com");
        List<Persons> personsList = new ArrayList<>(List.of(personJohn, personClara, personPaul));

        Firestation firestation1 = new Firestation("1509 Culver St", "1");
        Firestation firestation2 = new Firestation("456 Oak St", "2");
        List<Firestation> firestationList = new ArrayList<>(List.of(firestation1, firestation2));

        MedicalRecords medicalRecordsOfJohn = new MedicalRecords("John","Boyd","03/06/2020",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Collections.emptyList()
        );
        MedicalRecords medicalRecordsOfClara = new MedicalRecords("Clara","Boyd","04/11/2015",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                List.of("allergies:illisoxian")
        );
        MedicalRecords medicalRecordsOfPaul = new MedicalRecords("Paul","steph","03/06/1980",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                List.of("allergies:illisoxian")
        );
        List<MedicalRecords> medicalRecordsList = new ArrayList<>(List.of(medicalRecordsOfJohn, medicalRecordsOfClara, medicalRecordsOfPaul));

        when(jsonFileHandler.readJsonFile()).thenReturn(mockDataJsonFileHandler);
        lenient().when(calculateAgeService.calculateAge("03/06/2020")).thenReturn(5);

        mockDataJsonFileHandler.setFirestations(firestationList);
        mockDataJsonFileHandler.setPersons(personsList);
        mockDataJsonFileHandler.setMedicalrecords(medicalRecordsList);
    }

    @Test
    public void getHomeByStationTest() throws IOException {

        List<FloodHabitant> result = floodService.getHomeByStation(List.of("1", "2"));
        FloodHabitant habitantDetail = result.getFirst();

        assertEquals(3, result.size(), "Le nombre d'habitants retourné n'est pas correct");
        List<String> names = result.stream().map(floodHabitant -> floodHabitant.getFirstname()).toList();

        assertTrue(names.contains("John"), "John devrait être dans le résultat");
        assertTrue(names.contains("Clara"), "Clara devrait être dans le résultat");
        assertTrue(names.contains("Paul"), "Paul devrait être dans le résultat");

        assertEquals("John", habitantDetail.getFirstname());
        assertEquals("Boyd", habitantDetail.getLastname());
        assertEquals("841-874-6512", habitantDetail.getPhone());
        assertEquals(5, habitantDetail.getAge());
        assertEquals(List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                habitantDetail.getMedicalHistories().getFirst().getMedicine());

    }
}
