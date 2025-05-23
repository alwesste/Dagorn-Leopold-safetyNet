package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.MedicalRecord;
import com.openclassrooms.safetyNet.models.Person;
import com.openclassrooms.safetyNet.result.ChildAlert;
import com.openclassrooms.safetyNet.services.impl.CalculateAgeService;
import com.openclassrooms.safetyNet.services.impl.ChildAlertService;
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
public class ChildAlertServiceTest {

    @Mock
    JsonFileHandler jsonFileHandler;

    @Mock
    CalculateAgeService calculateAgeService;

    @InjectMocks
    ChildAlertService childAlertService;

    DataJsonHandler mockdataJsonHandler;

    @BeforeEach
    public void setUp() throws IOException {
        mockdataJsonHandler = new DataJsonHandler();

        Person child = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        Person adult = new Person("Peter", "Duncan", "644 Gershwin Cir", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        Person familyMember = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com");

        List <Person> personsList = new ArrayList<>(List.of(child, adult, familyMember));

        MedicalRecord medicalRecordsOfJohn = new MedicalRecord("John", "Boyd", "03/06/2020",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Collections.emptyList()
        );
        MedicalRecord medicalRecordsOfPeter = new MedicalRecord("Peter", "Duncan", "03/06/2000",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                List.of("allergies:illisoxian")
        );

        List<MedicalRecord> medicalRecordsList = new ArrayList<>(List.of(medicalRecordsOfJohn, medicalRecordsOfPeter));


        mockdataJsonHandler.setPersons(personsList);
        mockdataJsonHandler.setMedicalrecords(medicalRecordsList);
        System.out.println("Persons in mockdataJsonHandler: " + mockdataJsonHandler.getPersons());

        lenient().when(calculateAgeService.calculateAge("03/06/2020")).thenReturn(5);
        when(jsonFileHandler.readJsonFile()).thenReturn(mockdataJsonHandler);
    }

    @Test
    public void getListOfChildTest() throws IOException {
        List<ChildAlert> result = childAlertService.getListOfChild("1509 Culver St");

        assertFalse(result.isEmpty());

        ChildAlert childAlert = result.getFirst();

        assertEquals(1, result.size());
        assertEquals("John", childAlert.getFirstName());
        assertEquals("Boyd", childAlert.getLastName());
        assertEquals(5, childAlert.getAge());
        assertEquals(1, childAlert.getFamilyMembers().size());
        assertEquals("Tenley", childAlert.getFamilyMembers().getFirst().getFirstName());
    }

//    @Test
//    public void getListOfChildWithoutChildTest() throws IOException {
//        List<ChildAlert> result = childAlertService.getListOfChild("644 Gershwin Cir");
//
//        assertTrue(result.isEmpty());
//    }

    @Test
    public void getListIfChildWithWrongAddressTest() throws IOException {
        List<ChildAlert> result = childAlertService.getListOfChild("Wrong Address");

        assertNull(result);
    }
}
