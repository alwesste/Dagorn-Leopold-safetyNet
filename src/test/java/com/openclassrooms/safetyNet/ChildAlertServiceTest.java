package com.openclassrooms.safetyNet;

import com.openclassrooms.safetyNet.models.DataJsonHandler;
import com.openclassrooms.safetyNet.models.MedicalRecords;
import com.openclassrooms.safetyNet.models.Persons;
import com.openclassrooms.safetyNet.result.ChildAlert;
import com.openclassrooms.safetyNet.services.ChildAlertService;
import com.openclassrooms.safetyNet.services.JsonFileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ChildAlertServiceTest {

    @Mock
    JsonFileHandler jsonFileHandler;

    @InjectMocks
    ChildAlertService childAlertService;

    DataJsonHandler mockdataJsonHandler;

    @BeforeEach
    public void setUp() throws IOException {
        mockdataJsonHandler = new DataJsonHandler();
        Persons child = new Persons("John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
        Persons adult = new Persons("Peter","Duncan", "644 Gershwin Cir", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        Persons familyMember = new Persons("Tenley","Boyd","1509 Culver St","Culver","97451","841-874-6512","tenz@email.com");

        MedicalRecords medicalRecordsOfJohn = new MedicalRecords("John","Boyd","03/06/2020",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Collections.emptyList()
        );
        MedicalRecords medicalRecordsOfPeter = new MedicalRecords("Peter","Duncan","03/06/2000",
                Arrays.asList("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),
                Arrays.asList("allergies:illisoxian")
        );

        mockdataJsonHandler.setPersons(Arrays.asList(child, familyMember, adult));
        mockdataJsonHandler.setMedicalrecords(Arrays.asList(medicalRecordsOfJohn, medicalRecordsOfPeter));

        when(jsonFileHandler.readJsonFile()).thenReturn(mockdataJsonHandler);
    }

    @Test
    public void getListOfChildTest() throws IOException {
        List<ChildAlert> result = childAlertService.getListOfChild("1509 Culver St");

        ChildAlert childAlert = result.getFirst();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", childAlert.getFirstName());
        assertEquals("Boyd", childAlert.getLastName());
        assertEquals(5, childAlert.getAge());
        assertEquals(1, childAlert.getFamilyMembers().size());
        assertEquals("Tenley", childAlert.getFamilyMembers().getFirst().getFirstName());
    }

    @Test
    public void getListOfChildWithoutChildTest() throws IOException {
        List<ChildAlert> result = childAlertService.getListOfChild("644 Gershwin Cir");

        assertNull(result);
    }

    @Test
    public void getListIfChildWithWrongAddressTest() throws IOException {
        List<ChildAlert> result = childAlertService.getListOfChild("Wrong Address");

        assertNull(result);
    }
}
