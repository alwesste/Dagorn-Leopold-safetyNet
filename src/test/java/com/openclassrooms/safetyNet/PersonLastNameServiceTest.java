package com.openclassrooms.safetyNet;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.models.Persons;
import com.openclassrooms.safetyNet.result.MedicalHistory;
import com.openclassrooms.safetyNet.result.PersonInfoLastnameDetail;
import com.openclassrooms.safetyNet.services.impl.CalculateAgeService;
import com.openclassrooms.safetyNet.repository.JsonFileHandler;
import com.openclassrooms.safetyNet.services.impl.PersonLastNameSercice;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonLastNameServiceTest {

    @InjectMocks
    PersonLastNameSercice personLastNameSercice;

    @Mock
    JsonFileHandler jsonFileHandler;

    @Mock
    CalculateAgeService calculateAgeService;

    DataJsonHandler mockDataJsonFileHandler;

    @BeforeEach
    public void setUp() throws IOException {
        mockDataJsonFileHandler = new DataJsonHandler();

        Persons personJohn = new Persons("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "John@email.com");
        Persons personClara = new Persons("Clara", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "Clara@email.com");
        Persons personPaul = new Persons("Paul", "steph", "1509 Culver St", "Culver", "97451", "841-874-6512", "Paul@email.com");
        List<Persons> personsList = new ArrayList<>(List.of(personJohn, personClara, personPaul));

        MedicalRecords medicalRecordsOfJohn = new MedicalRecords("John", "Boyd", "03/06/2020",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Collections.emptyList()
        );
        MedicalRecords medicalRecordsOfClara = new MedicalRecords("Clara", "Boyd", "04/11/2015",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                List.of("allergies:illisoxian")
        );
        List<MedicalRecords> medicalRecordsList = new ArrayList<>(List.of(medicalRecordsOfJohn, medicalRecordsOfClara));

        when(jsonFileHandler.readJsonFile()).thenReturn(mockDataJsonFileHandler);
        lenient().when(calculateAgeService.calculateAge("03/06/2020")).thenReturn(5);

        mockDataJsonFileHandler.setPersons(personsList);
        mockDataJsonFileHandler.setMedicalrecords(medicalRecordsList);
    }

    @Test
    public void getPersonInfoFromLastNameTest() throws IOException {

        List<PersonInfoLastnameDetail> result = personLastNameSercice.getPersonInfoFromLastName("Boyd");

        assertFalse(result.isEmpty(), "La liste des résultats ne devrait pas être vide");

        PersonInfoLastnameDetail actualPerson = result.getFirst();

        MedicalHistory expectedMedicalHistory = new MedicalHistory(
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Collections.emptyList()
        );

        PersonInfoLastnameDetail expectedPerson = new PersonInfoLastnameDetail(
                "John",
                "Boyd",
                "1509 Culver St",
                5,
                "John@email.com",
                List.of(expectedMedicalHistory)
        );

        assertEquals(expectedPerson.getFirstName(), actualPerson.getFirstName(), "Le prénom est incorrect");
        assertEquals(expectedPerson.getLastName(), actualPerson.getLastName(), "Le nom est incorrect");
        assertEquals(expectedPerson.getAddress(), actualPerson.getAddress(), "L'adresse est incorrecte");
        assertEquals(expectedPerson.getAge(), actualPerson.getAge(), "L'âge est incorrect");
        assertEquals(expectedPerson.getEmail(), actualPerson.getEmail(), "L'email est incorrect");

        assertFalse(actualPerson.getMedicalHistories().isEmpty(), "L'historique médical ne devrait pas être vide");

        MedicalHistory actualMedicalHistory = actualPerson.getMedicalHistories().getFirst();

        assertEquals(expectedMedicalHistory.getMedicine(), actualMedicalHistory.getMedicine(), "Les médicaments ne correspondent pas");
        assertEquals(expectedMedicalHistory.getAllergie(), actualMedicalHistory.getAllergie(), "Les allergies ne correspondent pas");
    }
}
