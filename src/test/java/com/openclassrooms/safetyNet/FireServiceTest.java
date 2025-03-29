package com.openclassrooms.safetyNet;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.Firestations;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.models.Persons;
import com.openclassrooms.safetyNet.result.FireHabitantDetails;
import com.openclassrooms.safetyNet.services.CalculateAgeService;
import com.openclassrooms.safetyNet.services.FireService;
import com.openclassrooms.safetyNet.utils.JsonFileHandler;
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

        Firestations firestations1 = new Firestations("1509 Culver St", "1");
        List<Firestations> firestationsList = new ArrayList<>(List.of(firestations1));

        Persons personJohn = new Persons("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "John@email.com");

        MedicalRecords medicalRecordsOfJohn = new MedicalRecords("John","Boyd","03/06/2020",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Collections.emptyList()
        );

        mockdataJsonHandler.setFirestations(firestationsList);
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
