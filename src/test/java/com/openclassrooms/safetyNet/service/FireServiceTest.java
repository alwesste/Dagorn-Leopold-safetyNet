package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestation;
import com.openclassrooms.safetyNet.models.MedicalRecord;
import com.openclassrooms.safetyNet.models.Person;
import com.openclassrooms.safetyNet.result.FireHabitantDetails;
import com.openclassrooms.safetyNet.services.impl.CalculateAgeService;
import com.openclassrooms.safetyNet.services.impl.FireService;
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
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FireServiceTest {

    @InjectMocks
    FireService fireService;

    @Mock
    JsonFileHandler jsonFileHandler;

    @Mock
    CalculateAgeService calculateAgeService;

    DataJsonHandler mockdataJsonHandler;

    @BeforeEach
    public void setUp() throws IOException {
        mockdataJsonHandler = new DataJsonHandler();

        Firestation firestation1 = new Firestation("1509 Culver St", "1");
        List<Firestation> firestationList = new ArrayList<>(List.of(firestation1));

        Person personJohn = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "John@email.com");

        MedicalRecord medicalRecordsOfJohn = new MedicalRecord("John","Boyd","03/06/2020",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Collections.emptyList()
        );

        mockdataJsonHandler.setFirestations(firestationList);
        mockdataJsonHandler.setPersons(List.of(personJohn));
        mockdataJsonHandler.setMedicalrecords(List.of(medicalRecordsOfJohn));

        when(jsonFileHandler.readJsonFile()).thenReturn(mockdataJsonHandler);
        lenient().when(calculateAgeService.calculateAge("03/06/2020")).thenReturn(5);

    }

    @Test
    public void getFireHabitantByAdressTest() throws IOException {

        List<FireHabitantDetails> result = fireService.getFireHabitantByAdress("1509 Culver St");

        assertNotNull(result);
        assertEquals("John", result.getFirst().getFirstname());
        assertEquals("Boyd", result.getFirst().getLastname());
        assertEquals("841-874-6512", result.getFirst().getPhone());
        assertEquals("1", result.getFirst().getStationNumber());
        assertEquals(5, result.getFirst().getAge());
        assertEquals(
                List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                result.getFirst().getMedicalHistories().getFirst().getMedicine()
        );
        assertTrue(result.getFirst().getMedicalHistories().getFirst().getAllergie().isEmpty());

    }
}
